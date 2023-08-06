package ge.carapp.carappapi.service.order;

import ge.carapp.carappapi.config.AppConfig;
import ge.carapp.carappapi.entity.ManagerEntity;
import ge.carapp.carappapi.entity.OrderEntity;
import ge.carapp.carappapi.entity.ProductDetailsCarPrice;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.exception.GeneralException;
import ge.carapp.carappapi.models.bog.BogOrderStatus;
import ge.carapp.carappapi.models.bog.PaymentStatusInfo;
import ge.carapp.carappapi.models.bog.details.Client;
import ge.carapp.carappapi.models.bog.details.Item;
import ge.carapp.carappapi.models.bog.details.KeyValue;
import ge.carapp.carappapi.models.bog.details.OrderDetails;
import ge.carapp.carappapi.models.bog.details.PaymentDetail;
import ge.carapp.carappapi.models.bog.details.PurchaseUnits;
import ge.carapp.carappapi.models.bog.details.RedirectLinks;
import ge.carapp.carappapi.repository.OrderRepository;
import ge.carapp.carappapi.schema.CarType;
import ge.carapp.carappapi.schema.CommissionSchema;
import ge.carapp.carappapi.schema.order.OrderSchema;
import ge.carapp.carappapi.schema.ProductPackageSchema;
import ge.carapp.carappapi.schema.ProductSchema;
import ge.carapp.carappapi.schema.order.OrderInitializationResponse;
import ge.carapp.carappapi.schema.order.OrderInput;
import ge.carapp.carappapi.schema.order.OrderStatus;
import ge.carapp.carappapi.schema.payment.OrderProcessingResponse;
import ge.carapp.carappapi.service.ManagerService;
import ge.carapp.carappapi.service.ProductService;
import ge.carapp.carappapi.service.BookingService;
import ge.carapp.carappapi.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ge.carapp.carappapi.utils.PaymentUtils.convertPriceIntoDouble;
import static ge.carapp.carappapi.utils.PaymentUtils.convertPriceiIntoDoubleString;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final PaymentService paymentService;
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final BookingService bookingService;
    private final ManagerService managerService;
    private final AppConfig appConfig;

    public OrderInitializationResponse initializeOrder(UserEntity user, OrderInput order) {

        ProductSchema product = productService.getProductById(order.productId());
        List<ProductPackageSchema> packages = productService.getProductDetailsByProductId(order.productId());
        Optional<ProductPackageSchema> productPackageOptional =
                packages.stream().filter(p -> order.packageId().equals(p.id())).findAny();
        if (productPackageOptional.isEmpty()) {
            throw new GeneralException("Package does not exists");
        }
        ProductPackageSchema productPackage = productPackageOptional.get();

        // check if order with that idempotency key did not exist
        Optional<OrderEntity> orderByIdempotencyKey = orderRepository.findByIdempotencyKey(order.idempotencyKey());
        if (orderByIdempotencyKey.isPresent()) {
            throw new GeneralException("order by key already exists");
        }
        Optional<Integer> priceOptional = productPackage.pricesForCarTypes().stream()
                .filter(cp -> CarType.ALL.equals(cp.carType()) || cp.carType().equals(order.carType()))
                .findAny()
                .map(ProductDetailsCarPrice::price);

        if (priceOptional.isEmpty()) {
            throw new GeneralException("No price value found for car types");
        }
        int price = priceOptional.get();
        int commission = calculateCommission(product, productPackage);

        OrderEntity orderEntity = OrderEntity.builder()
                .idempotencyKey(order.idempotencyKey())
                .productId(order.productId())
                .packageId(order.packageId())
                .categoryId(product.categoryId())
                .providerId(product.providerId())
                .schedulingDate(order.schedulingDate())
                .schedulingTime(order.schedulingTime())
                .totalPrice(price + commission)
                .commission(commission)
                .carType(order.carType())
                .carPlateNumber(order.carPlateNumber())
                .comment(order.comment())
                .status(OrderStatus.NEW)
                .user(user)
                .cardId(order.cardId())
                .build();

        orderEntity = orderRepository.save(orderEntity);
        double commissionDouble = convertPriceIntoDouble(commission);

        Optional<OrderProcessingResponse> orderProcessingResponseOtp = paymentService.createOrder(user,
                PaymentService.createOrderRequest(
                        appConfig.getAppApi(),
                        orderEntity.getId(),
                        commissionDouble,
                        commissionDouble,
                        true,
                        product.id(),
                        product.name().ka(),
                        user.getFirstname() + user.getLastname()
                ),
                true,
                // todo check before saving
                Optional.ofNullable(order.cardId()),
                true
        ).blockOptional();

        if (orderProcessingResponseOtp.isEmpty()) {
            orderEntity.setStatus(OrderStatus.FAILED);
            orderEntity = orderRepository.save(orderEntity);
            throw new GeneralException("orderProcessingResponseOtp is empty");
        }

        OrderProcessingResponse paymentOrderCreationResponse = orderProcessingResponseOtp.get();

        orderEntity.setBogOrderId(paymentOrderCreationResponse.bogOrderId());
        orderEntity.setRedirectLink(paymentOrderCreationResponse.redirectLink());
        orderEntity.setIsAutomaticPayment(paymentOrderCreationResponse.isAutomaticPayment());
        orderEntity.setStatus(OrderStatus.PROCESSING);
        orderEntity = orderRepository.save(orderEntity);

        return OrderInitializationResponse.convert(orderEntity);
    }

    public CommissionSchema calculateCommission(UserEntity user, UUID productId, UUID packageId) {
        ProductSchema product = productService.getProductById(productId);
        List<ProductPackageSchema> packages = productService.getProductDetailsByProductId(productId);
        Optional<ProductPackageSchema> productPackageOptional =
                packages.stream().filter(p -> packageId.equals(p.id())).findAny();
        if (productPackageOptional.isEmpty()) {
            throw new GeneralException("Package does not exists");
        }
        ProductPackageSchema productPackage = productPackageOptional.get();

        int commission = calculateCommission(product, productPackage);
        return new CommissionSchema(commission, convertPriceiIntoDoubleString(commission));
    }

    public void processPaymentCallbackResponse(String orderId, PaymentStatusInfo paymentInfo) {
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(UUID.fromString(orderId));

        if (orderEntityOptional.isEmpty()) {
            throw new GeneralException("no order found by id");
        }

        OrderEntity orderEntity = orderEntityOptional.get();

        paymentService.savePayment(orderEntity.getUser(), orderEntity, paymentInfo);

        if (BogOrderStatus.COMPLETED.toLower().equals(paymentInfo.body().orderStatus().key())) {
            orderEntity.setStatus(OrderStatus.PAYED);
            orderEntity = orderRepository.save(orderEntity);
            orderEntity = bookingService.initializeBookingNotifications(orderEntity);

        } else if (BogOrderStatus.REJECTED.toLower().equals(paymentInfo.body().orderStatus().key())) {
            orderEntity.setStatus(OrderStatus.REJECTED);
            orderEntity.setErrorMessage(paymentInfo.body().rejectReason());
            orderEntity = orderRepository.save(orderEntity);
        }
    }

    private int calculateCommission(ProductSchema product, ProductPackageSchema productPackage) {
        return 20;
    }

    public List<OrderSchema> listUserOrders(UserEntity user) {
        return orderRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId()).stream().map(OrderSchema::convert).toList();
    }

    public List<OrderSchema> listProviderOrders(UserEntity user) {
        ManagerEntity manager = managerService.getManager(user);

        return orderRepository.findAllByProviderIdOrderByCreatedAtDesc(manager.getProviderId())
                .stream().map(OrderSchema::convert)
                .toList();
    }

    public void makeMockPaymentRequest(String orderId, boolean isSuccess) {
        PaymentStatusInfo mockedPayment = PaymentStatusInfo.builder()
                .requestTime(LocalDateTime.now().toString())
                .body(OrderDetails.builder()
                        .lang("ka")
                        .rejectReason(null)
                        .capture("automatic")
                        .industry("ecommerce")
                        .externalOrderId(orderId)
                        .orderStatus(isSuccess ? new KeyValue("completed", "წარმატებული") : new KeyValue("rejected", "წარუმატებული"))
                        .paymentDetail(PaymentDetail.builder()
                                .transactionId("230513868679")
                                .transferMethod(new KeyValue("card", "საბარათე გადახდა"))
                                .cardExpiryDate("03/24")
                                .paymentOption("direct_debit")
                                .payerIdentifier("548888xxxxxx9893")
                                .cardType("mc")
                                .build())
                        .redirectLinks(new RedirectLinks("success redirect lin", "fail redirect link"))
                        .client(new Client("10000", "საქართველოს ბანკი", "BOG", "https://api.bog.ge"))
                        .purchaseUnits(new PurchaseUnits(
                                "20",
                                "20"
                                , "0",
                                "GEL",
                                List.of(new Item("id_1", "description", "1", "20"))
                        ))
                        .build())

                .build();

        processPaymentCallbackResponse(orderId, mockedPayment);
    }
}

package ge.carapp.carappapi.service;

import ge.carapp.carappapi.entity.CarEntity;
import ge.carapp.carappapi.exception.GeneralException;
import ge.carapp.carappapi.repository.CarRepository;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.schema.CarSchema;
import ge.carapp.carappapi.schema.graphql.CarInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarService {
    private final CarRepository carRepository;

    public List<CarSchema> getCars(UserEntity user) {
        return user.getCarList().stream().map(CarSchema::convert).toList();
    }

    public CarSchema addCar(UserEntity user, CarInput carInput) {
        List<CarEntity> existingCarsForUser = user.getCarList().stream().filter(
                car -> car.getPlateNumber().equals(carInput.getPlateNumber())
        ).toList();


        if (existingCarsForUser.size() > 1) {
            throw new GeneralException("car with plate number: %s already exists for user %s"
                    .formatted(carInput.getPlateNumber(), user.getId()));
        }

        // TODO: What to do if car with plate number already exists

        CarEntity carEntity = CarEntity.builder()
                .owner(user)
                .plateNumber(carInput.getPlateNumber())
                .carType(carInput.getCarType())
                .techPassportNumber(carInput.getTechPassportNumber())
                .vin(carInput.getVin())
                .make(carInput.getMake())
                .model(carInput.getModel())
                .year(carInput.getYear())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        carRepository.save(carEntity);

        log.info("Added Car for User : {} ", user.getId());
        return CarSchema.convert(carEntity);
    }

    public boolean removeCar(UserEntity user, UUID carId) {
        List<CarEntity> existingCarsForUser = user.getCarList().stream().filter(
                car -> car.getId().equals(carId)
        ).toList();

        if (existingCarsForUser.size() == 1) {
            carRepository.deleteById(carId);
            return true;
        }
        log.warn("User : {} does not own car by id {} to remove", user.getId(), carId);
        return false;
    }

    public CarSchema updateCar(UserEntity user, UUID carId, CarInput carInput) {
        List<CarEntity> existingCarsForUser = user.getCarList().stream().filter(
                car -> car.getId().equals(carId)
        ).toList();

        if (existingCarsForUser.size() != 1) {
            log.warn("User : {} does not own car by id {} to update", user.getId(), carId);
            throw new GeneralException("user does not own the car to update");
        }

        CarEntity carEntity = existingCarsForUser.get(0);


        carEntity.setPlateNumber(carInput.getPlateNumber());
        carEntity.setCarType(carInput.getCarType());
        carEntity.setTechPassportNumber(carInput.getTechPassportNumber());
        carEntity.setVin(carInput.getVin());
        carEntity.setMake(carInput.getMake());
        carEntity.setModel(carInput.getModel());
        carEntity.setYear(carInput.getYear());
        carEntity.setCreatedAt(LocalDateTime.now());
        carEntity.setUpdatedAt(LocalDateTime.now());

        carRepository.save(carEntity);

        log.info("Added Car for User : {} ", user.getId());
        return CarSchema.convert(carEntity);
    }

}

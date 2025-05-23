package ge.carapp.carappapi.service;

import ge.carapp.carappapi.entity.CarEntity;
import ge.carapp.carappapi.exception.GeneralException;
import ge.carapp.carappapi.repository.CarRepository;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.schema.CarSchema;
import ge.carapp.carappapi.schema.graphql.CarInput;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarService {
    private final CarRepository carRepository;

    public List<CarSchema> getCars(UserEntity user) {
        return carRepository.findAllByOwnerId(user.getId())
            .stream().map(CarSchema::convert).toList();
    }

    @Transactional
    public CarSchema addCar(UserEntity user, CarInput carInput) {
        List<CarEntity> carList = carRepository.findAllByOwnerId(user.getId());
        List<CarEntity> existingCarsForUser = carList.stream().filter(
            car -> car.getPlateNumber().equals(carInput.plateNumber())
        ).toList();


        if (!existingCarsForUser.isEmpty()) {
            throw new GeneralException("car with plate number: %s already exists for user %s"
                .formatted(carInput.plateNumber(), user.getId()));
        }

        // TODO: What to do if car with plate number already exists

        CarEntity carEntity = CarEntity.builder()
            .owner(user)
            .plateNumber(carInput.plateNumber())
            .carType(carInput.carType())
            .techPassportNumber(carInput.techPassportNumber())
            .vin(carInput.vin())
            .make(carInput.make())
            .model(carInput.model())
            .year(carInput.year())
            .build();

        carEntity = carRepository.save(carEntity);

        log.info("Added Car for User : {} ", user.getId());
        return CarSchema.convert(carEntity);
    }

    public boolean removeCar(UserEntity user, UUID carId) {
        List<CarEntity> carList = carRepository.findAllByOwnerId(user.getId());
        List<CarEntity> existingCarsForUser = carList.stream().filter(
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
        List<CarEntity> carList = carRepository.findAllByOwnerId(user.getId());
        List<CarEntity> existingCarsForUser = carList.stream().filter(
            car -> car.getId().equals(carId)
        ).toList();

        if (existingCarsForUser.size() != 1) {
            log.warn("User : {} does not own car by id {} to update", user.getId(), carId);
            throw new GeneralException("user does not own the car to update");
        }

        CarEntity carEntity = existingCarsForUser.get(0);


        carEntity.setPlateNumber(carInput.plateNumber());
        carEntity.setCarType(carInput.carType());
        carEntity.setTechPassportNumber(carInput.techPassportNumber());
        carEntity.setVin(carInput.vin());
        carEntity.setMake(carInput.make());
        carEntity.setModel(carInput.model());
        carEntity.setYear(carInput.year());

        carEntity = carRepository.save(carEntity);

        log.info("Added Car for User : {} ", user.getId());
        return CarSchema.convert(carEntity);
    }

}

package org.carrental.service;

import org.carrental.exception.CarNotFoundException;
import org.carrental.exception.ValidationException;
import org.carrental.model.car.Car;
import org.carrental.model.car.CarStatus;
import org.carrental.repository.CarRepository;

import java.util.List;

public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car create(Car car) {
        if (car.getMake().isBlank()){
            throw new ValidationException("make", "Cannot be blank");
        }
        if (car.getModel().isBlank()){
            throw new ValidationException("model", "Cannot be blank");
        }
        if (car.getVin().isBlank()) {
            throw new ValidationException("vin", "cannot be blank");
        }
        if (car.getVin().length() != 3){
            throw new ValidationException("vin", "length must be 3");
        }

        return carRepository.create(car);
    }

    public List<Car> getAvailableCars(){
        return carRepository.getByStatus(CarStatus.AVAILABLE);
    }

    public List<Car> getAllCars(){
        return carRepository.getAll();
    }

    public Car getById(Integer id){
        if (id == null){
            throw new ValidationException("id", "cannot be null");
        }

        return carRepository.getById(id)
                .orElseThrow( ()-> new CarNotFoundException("Car does not exist"));
    }

}
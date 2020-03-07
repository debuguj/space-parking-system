package pl.debuguj.parkingspacessystem.spot.validation;

import pl.debuguj.parkingspacessystem.spot.Spot;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidParkingSpaceFinishedValidator implements ConstraintValidator<ValidParkingSpaceFinished, Spot> {
    @Override
    public void initialize(ValidParkingSpaceFinished validParkingSpaceFinished) {

    }

    @Override
    public boolean isValid(Spot spotFinished, ConstraintValidatorContext constraintValidatorContext) {

        return false;
    }
}

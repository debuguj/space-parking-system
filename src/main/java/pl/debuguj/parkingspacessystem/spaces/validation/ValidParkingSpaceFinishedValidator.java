package pl.debuguj.parkingspacessystem.spaces.validation;

import pl.debuguj.parkingspacessystem.spaces.SpaceFinished;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidParkingSpaceFinishedValidator implements ConstraintValidator<ValidParkingSpaceFinished, SpaceFinished> {
    @Override
    public void initialize(ValidParkingSpaceFinished validParkingSpaceFinished) {

    }

    @Override
    public boolean isValid(SpaceFinished spaceFinished, ConstraintValidatorContext constraintValidatorContext) {

        return false;
    }
}

package pl.debuguj.parkingspacessystem.spot.validation;

import pl.debuguj.parkingspacessystem.spot.Spot;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class FarePeriodValidator implements ConstraintValidator<FarePeriod, Spot> {
    @Override
    public void initialize(FarePeriod constraintAnnotation) {

    }

    @Override
    public boolean isValid(Spot spot, ConstraintValidatorContext constraintValidatorContext) {
        //TODO: update code below
        if (Objects.isNull(spot) || Objects.isNull(spot.getBeginDate())) {
            return false;
        } else if (Objects.isNull(spot.getFinishDate())) {
            return true;
        } else if (spot.getBeginDate().before(spot.getFinishDate())) {
            return true;
        }
        return false;
    }
}

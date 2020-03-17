package pl.debuguj.system.spot;

import pl.debuguj.system.spot.DriverType;
import pl.debuguj.system.spot.DriverTypeSubSet;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class DriverTypeSubSetValidator implements ConstraintValidator<DriverTypeSubSet, DriverType> {
    private DriverType[] subset;

    @Override
    public void initialize(DriverTypeSubSet constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(DriverType value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}
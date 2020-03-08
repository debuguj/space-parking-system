package pl.debuguj.parkingspacessystem.spot.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeFormatValidator implements ConstraintValidator<DateTimeFormat, Date> {


    @Override
    public void initialize(DateTimeFormat constraintAnnotation) {

    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}

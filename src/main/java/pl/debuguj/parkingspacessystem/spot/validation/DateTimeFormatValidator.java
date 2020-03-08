package pl.debuguj.parkingspacessystem.spot.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;

public class DateTimeFormatValidator implements ConstraintValidator<DateTimeFormat, String> {

    private String pattern;

    @Override
    public void initialize(DateTimeFormat dateTimeFormat) {
        this.pattern = dateTimeFormat.pattern();
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintValidatorContext) {
        if (object == null) {
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(pattern);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

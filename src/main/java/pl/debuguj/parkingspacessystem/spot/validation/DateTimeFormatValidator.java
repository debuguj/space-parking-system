package pl.debuguj.parkingspacessystem.spot.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        try {
            //TODO update checking algorithm
            Date date = new SimpleDateFormat(pattern).parse(object);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

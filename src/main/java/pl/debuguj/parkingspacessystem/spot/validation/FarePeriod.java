package pl.debuguj.parkingspacessystem.spot.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = FarePeriodValidator.class)
@Documented
public @interface FarePeriod {
    String message() default "{message.key}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

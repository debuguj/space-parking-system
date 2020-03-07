package pl.debuguj.parkingspacessystem.spot.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {ValidParkingSpaceFinishedValidator.class})
@Documented
public @interface ValidParkingSpaceFinished {
    String message() default "{pl.debuguj.parkingspacessystem.domain" + "public @interface ValidateParkingSpaceFinished {\n.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
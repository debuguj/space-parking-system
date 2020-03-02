package pl.debuguj.parkingspacessystem.spaces.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = DateTimeFormatValidator.class)
@Documented
public @interface DateFormat {
    String message() default "{message.key}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String pattern();
}

package com.abdulpitodia.distributed_id_counter.validators.annotations;

import com.abdulpitodia.distributed_id_counter.validators.EndpointValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EndpointValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEndPoint {

    String message() default "Invalid HTTP endpoint";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}

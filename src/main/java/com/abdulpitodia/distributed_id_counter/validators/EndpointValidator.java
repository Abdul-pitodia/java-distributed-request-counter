package com.abdulpitodia.distributed_id_counter.validators;

import com.abdulpitodia.distributed_id_counter.validators.annotations.ValidEndPoint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class EndpointValidator implements ConstraintValidator<ValidEndPoint, String> {
    @Override
    public boolean isValid(String endPoint, ConstraintValidatorContext constraintValidatorContext) {
        if (endPoint == null) return true;

        if (endPoint.isBlank() || endPoint.isEmpty()) return false;

        return StringUtils.startsWithIgnoreCase(endPoint, "http://") || StringUtils.startsWithIgnoreCase(endPoint, "https://");
    }
}

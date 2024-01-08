package com.setyawandwiki.ecommerce.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService{
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    @Override
    public void validate(Object request) {
        Set<ConstraintViolation<Object>> validate = validator.validate(request);
        if(!validate.isEmpty()){
            throw new ConstraintViolationException(validate);
        }
    }
}

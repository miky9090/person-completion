package com.mlestyan.validation.document.validation.constraint;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintViolation;
import javax.validation.Payload;

import com.mlestyan.validation.document.validation.constraint.ValidDocument.List;
import com.mlestyan.validation.document.validation.impl.DocumentValidator;

/**
 * @deprecated Using Bean Validation is better choice if we only want to return the error messages only without any other contents, 
 * because we cannot reconstruct the {@link ConstraintViolation}-s on the other end and merge it with the existing violations.
 * */
@Documented
@Retention(RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
		ElementType.PARAMETER, ElementType.TYPE_USE })
@Constraint(validatedBy = {DocumentValidator.class})
@Repeatable(List.class)
public @interface ValidDocument {
	String message() default "1: szig: 6 szám + 2 betű; 2: utl 2 betű + 7 szám; 3: ven: nemtom, egyéb bármi max 10";
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
	
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
		ElementType.PARAMETER, ElementType.TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
		ValidDocument[] value();
    }
}

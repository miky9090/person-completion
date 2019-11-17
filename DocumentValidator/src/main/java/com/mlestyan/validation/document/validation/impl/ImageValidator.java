package com.mlestyan.validation.document.validation.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;

import com.mlestyan.validation.document.validation.constraint.Image;
import com.mlestyan.validation.document.validation.constraint.Image.SupportedImageFormat;

/**
 * @deprecated Using Bean Validation is better choice if we only want to return the error messages only without any other contents, 
 * because we cannot reconstruct the {@link ConstraintViolation}-s on the other end and merge it with the existing violations.
 * */
public class ImageValidator implements ConstraintValidator<Image, byte[]> {
	private SupportedImageFormat[] supportedFormats;
	private int width;
	private int height;
	
	@Override
	public void initialize(Image constraintAnnotation) {
		this.supportedFormats = constraintAnnotation.supportedFormats();
		this.width = constraintAnnotation.width();
		this.height = constraintAnnotation.height();
	}

	@Override
	public boolean isValid(byte[] value, ConstraintValidatorContext context) {
		if(value == null) {
			return true;
		}
		
		ByteArrayInputStream bis = new ByteArrayInputStream(value);
		try {
			ImageInputStream imageInputStream = ImageIO.createImageInputStream(bis);
			Iterable<ImageReader> imageReaderIterables = () -> ImageIO.getImageReaders(imageInputStream);
			Stream<ImageReader> imageReaderStream = StreamSupport.stream(imageReaderIterables.spliterator(), false);
			return imageReaderStream.anyMatch(imageReader -> {
				try {
					imageReader.setInput(imageInputStream);
					return Stream.of(supportedFormats).anyMatch(supportedFormat -> {
						try {
							return supportedFormat.toString().equalsIgnoreCase(imageReader.getFormatName()) && imageReader.getWidth(0) == this.width && imageReader.getHeight(0) == this.height;
						}
						catch (IOException e) {
							return false;
						}
					});
				}
				finally {
					imageReader.dispose();
				}
			});
		}
		catch (IOException e) {
			// Failed to read image
			return false;
		}
	}

}

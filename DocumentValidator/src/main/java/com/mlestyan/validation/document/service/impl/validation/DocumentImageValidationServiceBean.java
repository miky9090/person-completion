package com.mlestyan.validation.document.service.impl.validation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.mlestyan.validation.document.configuration.property.DocumentValidationProperties;
import com.mlestyan.validation.document.service.contract.validation.DocumentImageValidationService;

@Service
@EnableConfigurationProperties(DocumentValidationProperties.class)
public class DocumentImageValidationServiceBean implements DocumentImageValidationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DocumentImageValidationServiceBean.class);
	
	@Autowired
	private DocumentValidationProperties documentValidationProperties;

	/**
	 * Validates an image represented as a byte array. Validation constraints are defined in the application property file.
	 * @param the image represented as a byte array
	 * @return true if the image has appropriate format and size
	 * */
	@Override
	public boolean validateDocumentImage(byte[] documentImage) {
		LOGGER.debug("Validating document image...");
		ByteArrayInputStream bis = new ByteArrayInputStream(documentImage);
		
		try(ImageInputStream imageInputStream = ImageIO.createImageInputStream(bis)) {
			if(imageInputStream == null) {
				LOGGER.debug("Invalid document image format. Cannot read document image.");
				return false;
			}
			
			Iterable<ImageReader> imageReaderIterables = () -> ImageIO.getImageReaders(imageInputStream);
			Stream<ImageReader> imageReaderStream = StreamSupport.stream(imageReaderIterables.spliterator(), false);
			return imageReaderStream.anyMatch(imageReader -> {
				try {
					imageReader.setInput(imageInputStream);
					LOGGER.debug("Checking if the image format is supported...");
					return Stream.of(documentValidationProperties.getSupportedImageFormats()).anyMatch(supportedFormat -> {
						try {
							return supportedFormat.toString().equalsIgnoreCase(imageReader.getFormatName()) && imageReader.getWidth(0) == this.documentValidationProperties.getImageWidth() && imageReader.getHeight(0) == this.documentValidationProperties.getImageHeight();
						}
						catch (IOException e) {
							LOGGER.debug("Invalid document image format. Unable to read image information from the document image!");
							return false;
						}
					});
				}
				finally {
					LOGGER.debug("Disposing image reader...");
					imageReader.dispose();
				}
			});
		}
		catch (IOException e) {
			LOGGER.error("Failed to read document image!");
			return false;
		}
	}

}

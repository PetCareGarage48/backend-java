package com.core.app.exceptionHandler;

import com.core.app.entities.dto.ErrorPath;
import com.core.app.entities.dto.Response;
import com.core.app.utils.Helper;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.core.app.constants.GeneralConstants.LOG_THIS;
import static com.core.app.constants.GeneralConstants.SOMETHING_WENT_WRONG;
import static com.core.app.constants.GeneralConstants.TRUE;

@ControllerAdvice
public class DefaultExceptionHandler  {

	private final Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleException(HttpServletRequest request, Exception e){
		String errorMessage = SOMETHING_WENT_WRONG + e.toString();
		ResponseEntity<Response> errorResponse = Helper.buildHttpResponse(
				HttpStatus.INTERNAL_SERVER_ERROR,
				TRUE,
				errorMessage,
				ErrorPath.builder().path(request.getRequestURI()).build()
		);
		logger.error(LOG_THIS, errorResponse.getBody());
		return errorResponse;
	}
}
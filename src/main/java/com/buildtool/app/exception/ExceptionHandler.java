package com.buildtool.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.buildtool.app.utils.BaseResponse;

@ControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(BuildToolWebException.class)
	public ResponseEntity<BaseResponse> sendFailureResponse(BuildToolWebException ex) {
		System.out.println("inside exception handler");
		BaseResponse response = new BaseResponse();
		response.setResponseCode(500);
		response.setResponseMessage(ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

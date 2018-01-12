package com.buildtool.app.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;

@Component
public class DefaultException extends DefaultErrorAttributes {

	@Override
	public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {

		Throwable throwable = getError(requestAttributes);
		Exception myException=(Exception)throwable;
		Map<String, Object> customErrorAttributes = new HashMap<>();
		customErrorAttributes.put("responseCode", 500);
		customErrorAttributes.put("responseMessage", myException.getMessage());
		return customErrorAttributes;
	}

}

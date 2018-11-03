package com.core.app.services;


import com.core.app.entities.dto.Response;
import org.springframework.http.ResponseEntity;

public interface MessagePublisher {

	ResponseEntity<Response> publish(final String topic, final String message);
}

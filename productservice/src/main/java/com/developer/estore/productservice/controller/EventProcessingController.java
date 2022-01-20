package com.developer.estore.productservice.controller;

import java.util.Optional;

import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class EventProcessingController {

	@Autowired
	private EventProcessingConfiguration configuration;
	
	@PostMapping("/eventProcessor/{processName}/reset")
	public ResponseEntity<String> replayEvents(@PathVariable String processName){
		
		Optional<TrackingEventProcessor> processor =configuration.eventProcessor(processName,TrackingEventProcessor.class);
		if(processor.isPresent()) {
			TrackingEventProcessor eventProcessor = processor.get();
			eventProcessor.shutDown();
			eventProcessor.resetTokens();
			eventProcessor.start();
			
			return ResponseEntity.ok()
					.body(String.format("Event processor with name %s has been reset",processName));
		}
		else {
			return ResponseEntity.badRequest().body("Only TrackingEventProcessor is supported");
		}
	}
}

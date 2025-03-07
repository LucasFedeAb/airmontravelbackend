package com.airmont.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	 @Override
	    public void configureMessageBroker(MessageBrokerRegistry config) {
	        config.enableSimpleBroker("/topic"); // prefijo para los destinos de los mensajes
	        config.setApplicationDestinationPrefixes("/app"); // prefijo para los mensajes enviados desde el cliente
	    }

	    @Override
	    public void registerStompEndpoints(StompEndpointRegistry registry) {
	    	registry.addEndpoint("/ws").setAllowedOrigins("https://airmontravel.com", "https://airmontravel.store", "https://airmontravel.vercel.app/");
	    }

}

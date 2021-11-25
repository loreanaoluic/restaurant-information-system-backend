package com.app.restaurant.websockets;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    /*
     * Metoda registruje Stomp (https://stomp.github.io/) endpoint i koristi SockJS JavaScript biblioteku
     * (https://github.com/sockjs)
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/notification")

                .setAllowedOrigins("*")
                .withSockJS();
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app") // Prefiks koji koji se koristi za mapiranje svih poruka.
                .enableSimpleBroker("/topic"); // Definisanje topic-a (ruta) na koje klijenti mogu da se pretplate.

    }



}

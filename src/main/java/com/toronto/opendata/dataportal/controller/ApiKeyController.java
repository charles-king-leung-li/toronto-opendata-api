package com.toronto.opendata.dataportal.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/config")
public class ApiKeyController {
    
    @Value("${google.maps.api.key}")
    private String googleMapsApiKey;
    
    /**
     * Endpoint to provide the Google Maps API key to the frontend
     * Note: Only expose this if your API key has proper domain restrictions!
     */
    @GetMapping("/maps-key")
    public Map<String, String> getGoogleMapsApiKey() {
        Map<String, String> response = new HashMap<>();
        response.put("apiKey", googleMapsApiKey);
        return response;
    }
}

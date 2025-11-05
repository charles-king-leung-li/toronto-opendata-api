package com.toronto.opendata.dataportal.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toronto.opendata.dataportal.model.MultiPointModel;
import org.springframework.stereotype.Component;

/**
 * Utility class for parsing geometry data from various formats.
 */
@Component
public class GeometryParser {
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    /**
     * Parses a GeoJSON geometry string into a MultiPointModel.
     * Expected format: {"coordinates": [[-79.48576, 43.685]], "type": "MultiPoint"}
     * 
     * @param geometryJson The JSON string containing geometry data
     * @return MultiPointModel with x (longitude), y (latitude), and type
     * @throws RuntimeException if parsing fails
     */
    public static MultiPointModel parseGeometry(String geometryJson) {
        if (geometryJson == null || geometryJson.trim().isEmpty()) {
            return null;
        }
        
        try {
            JsonNode root = objectMapper.readTree(geometryJson);
            
            // Extract type
            String type = root.has("type") ? root.get("type").asText() : "Unknown";
            
            // Extract coordinates (first point if MultiPoint has multiple)
            JsonNode coordinates = root.get("coordinates");
            if (coordinates != null && coordinates.isArray() && coordinates.size() > 0) {
                JsonNode firstPoint = coordinates.get(0);
                if (firstPoint.isArray() && firstPoint.size() >= 2) {
                    double x = firstPoint.get(0).asDouble(); // longitude
                    double y = firstPoint.get(1).asDouble(); // latitude
                    
                    return MultiPointModel.builder()
                            .x(x)
                            .y(y)
                            .type(type)
                            .build();
                }
            }
            
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse geometry JSON: " + geometryJson, e);
        }
    }
    
    /**
     * Alternative method that returns a default MultiPointModel on error instead of throwing exception
     */
    public static MultiPointModel parseGeometrySafe(String geometryJson) {
        try {
            return parseGeometry(geometryJson);
        } catch (Exception e) {
            // Return a default/empty model instead of throwing
            return MultiPointModel.builder()
                    .x(0.0)
                    .y(0.0)
                    .type("Unknown")
                    .build();
        }
    }
}

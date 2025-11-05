package com.toronto.opendata.dataportal.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toronto.opendata.dataportal.model.CulturalHotSpotModel;
import com.toronto.opendata.dataportal.service.PointsOfInterestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@Tag(name = "CultureHotspot", description = "CultureHotSpot")
public class CultureHotSpotController {
    // private String idString = "cultural-hotspot-points-of-interest";
    
    private final PointsOfInterestService pointsOfInterestService;
    
    public CultureHotSpotController(PointsOfInterestService pointsOfInterestService) {
        this.pointsOfInterestService = pointsOfInterestService;
    }

    @Operation(
        summary = "Culture HotSpot Endpoint",
        description = "Returns cultural hotspot points of interest from Toronto Open Data"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Successfully retrieved"
    )
    @ApiResponse(
        responseCode = "500",
        description = "Internal server error"
    )
    
    @GetMapping("/cultureHotSpot")
    public List<CulturalHotSpotModel> cultureHotSpot() {
        // Get cultural hotspots from service
        List<CulturalHotSpotModel> models = pointsOfInterestService.getCulturalHotSpots();
        
        // TODO: Transform to DTOs before returning
        // return culturalHotSpotTransformer.toDTOList(models);
        
        return models;
    }
}
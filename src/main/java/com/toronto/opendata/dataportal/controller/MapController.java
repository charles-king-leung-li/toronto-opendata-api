package com.toronto.opendata.dataportal.controller;

import com.toronto.opendata.dataportal.dto.GeoJsonFeatureCollectionDTO;
import com.toronto.opendata.dataportal.dto.MapPointDTO;
import com.toronto.opendata.dataportal.service.MapService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/map")
@Tag(name = "Map", description = "Endpoints for map visualization of Toronto cultural hotspots")
public class MapController {
    
    private final MapService mapService;
    
    public MapController(MapService mapService) {
        this.mapService = mapService;
    }
    
    @Operation(
        summary = "Get all map points",
        description = "Returns all cultural hotspots with coordinates as simple map points"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved map points")
    @GetMapping("/points")
    public List<MapPointDTO> getAllMapPoints() {
        return mapService.getMapPoints();
    }
    
    @Operation(
        summary = "Get GeoJSON FeatureCollection",
        description = "Returns cultural hotspots in GeoJSON format compatible with Leaflet, Mapbox, Google Maps, etc."
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved GeoJSON data")
    @GetMapping("/geojson")
    public GeoJsonFeatureCollectionDTO getGeoJson() {
        return mapService.getGeoJsonFeatureCollection();
    }
    
    @Operation(
        summary = "Get map points within bounding box",
        description = "Returns cultural hotspots within the specified geographic bounds"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved filtered map points")
    @GetMapping("/points/bounds")
    public List<MapPointDTO> getMapPointsInBounds(
            @Parameter(description = "Minimum latitude") @RequestParam Double minLat,
            @Parameter(description = "Maximum latitude") @RequestParam Double maxLat,
            @Parameter(description = "Minimum longitude") @RequestParam Double minLon,
            @Parameter(description = "Maximum longitude") @RequestParam Double maxLon
    ) {
        return mapService.getMapPointsInBounds(minLat, maxLat, minLon, maxLon);
    }
    
    @Operation(
        summary = "Get nearby map points",
        description = "Returns cultural hotspots within specified radius (in kilometers) from a center point"
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved nearby points")
    @GetMapping("/points/nearby")
    public List<MapPointDTO> getNearbyMapPoints(
            @Parameter(description = "Center latitude") @RequestParam Double lat,
            @Parameter(description = "Center longitude") @RequestParam Double lon,
            @Parameter(description = "Radius in kilometers", example = "5.0") @RequestParam(defaultValue = "5.0") Double radiusKm
    ) {
        return mapService.getMapPointsNearby(lat, lon, radiusKm);
    }
}

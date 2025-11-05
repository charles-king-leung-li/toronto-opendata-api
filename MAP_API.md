# Map API Endpoints

This module provides map-based endpoints for visualizing Toronto cultural hotspots using their geographic coordinates.

## Endpoints

### 1. Get All Map Points
```
GET /api/map/points
```
Returns all cultural hotspots as simple map points with coordinates.

**Response Example:**
```json
[
  {
    "id": "123",
    "name": "Art Gallery",
    "description": "Beautiful art gallery",
    "address": "123 Main St",
    "type": "Art",
    "latitude": 43.685,
    "longitude": -79.48576,
    "imageUrl": "https://..."
  }
]
```

### 2. Get GeoJSON FeatureCollection
```
GET /api/map/geojson
```
Returns cultural hotspots in standard GeoJSON format, compatible with:
- Leaflet
- Mapbox
- Google Maps
- ArcGIS
- Any GeoJSON-compatible mapping library

**Response Example:**
```json
{
  "type": "FeatureCollection",
  "features": [
    {
      "type": "Feature",
      "geometry": {
        "type": "Point",
        "coordinates": [-79.48576, 43.685]
      },
      "properties": {
        "id": "123",
        "name": "Art Gallery",
        "description": "Beautiful art gallery",
        "address": "123 Main St",
        "type": "Art",
        "imageUrl": "https://..."
      }
    }
  ]
}
```

### 3. Get Points Within Bounding Box
```
GET /api/map/points/bounds?minLat=43.6&maxLat=43.7&minLon=-79.5&maxLon=-79.3
```
Returns cultural hotspots within specified geographic bounds.

**Parameters:**
- `minLat` - Minimum latitude
- `maxLat` - Maximum latitude
- `minLon` - Minimum longitude
- `maxLon` - Maximum longitude

### 4. Get Nearby Points
```
GET /api/map/points/nearby?lat=43.6532&lon=-79.3832&radiusKm=5.0
```
Returns cultural hotspots within a specified radius from a center point.

**Parameters:**
- `lat` - Center latitude
- `lon` - Center longitude
- `radiusKm` - Radius in kilometers (default: 5.0)

## Map Visualization

A demo map visualization is available at:
```
http://localhost:8080/map.html
```

This interactive map uses Leaflet.js to display all cultural hotspots with clickable markers.

## Coordinate System

- **Longitude (x)**: East/West position (-180 to 180)
- **Latitude (y)**: North/South position (-90 to 90)
- **Format**: GeoJSON standard `[longitude, latitude]`

## Usage Examples

### JavaScript/Fetch
```javascript
// Get all points
fetch('/api/map/points')
  .then(response => response.json())
  .then(points => console.log(points));

// Get GeoJSON for Leaflet
fetch('/api/map/geojson')
  .then(response => response.json())
  .then(geojson => L.geoJSON(geojson).addTo(map));
```

### cURL
```bash
# Get all map points
curl http://localhost:8080/api/map/points

# Get GeoJSON
curl http://localhost:8080/api/map/geojson

# Get nearby points (5km radius from downtown Toronto)
curl "http://localhost:8080/api/map/points/nearby?lat=43.6532&lon=-79.3832&radiusKm=5"
```

## Distance Calculation

The nearby endpoint uses the Haversine formula to calculate accurate distances on Earth's surface, accounting for its curvature.

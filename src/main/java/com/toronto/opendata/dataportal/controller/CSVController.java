package com.toronto.opendata.dataportal.controller;

import com.toronto.opendata.dataportal.service.CSVReaderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/csv")
@Tag(name = "CSV Operations", description = "API endpoints for CSV file operations")
public class CSVController {

    private final CSVReaderService csvReaderService;

    public CSVController(CSVReaderService csvReaderService) {
        this.csvReaderService = csvReaderService;
    }

    @GetMapping("/headers")
    @Operation(summary = "Get CSV Headers", description = "Returns the header names from a CSV file")
    public ResponseEntity<List<String>> getHeaders(
            @Parameter(description = "Path to the CSV file")
            @RequestParam String filePath) {
        try {
            if (!csvReaderService.isValidCSVFile(filePath)) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(csvReaderService.readCSVHeaders(filePath));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/read")
    @Operation(summary = "Read CSV Content", description = "Returns the entire content of a CSV file")
    public ResponseEntity<List<Map<String, String>>> readCSV(
            @Parameter(description = "Path to the CSV file")
            @RequestParam String filePath) {
        try {
            if (!csvReaderService.isValidCSVFile(filePath)) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(csvReaderService.readCSV(filePath));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_PLAIN_VALUE)
    @Operation(summary = "Stream CSV Content", description = "Streams the content of a CSV file")
    public ResponseEntity<StreamingResponseBody> streamCSV(
            @Parameter(description = "Path to the CSV file")
            @RequestParam String filePath,
            @Parameter(description = "Optional filename for download")
            @RequestParam(required = false) String downloadFilename) {
        
        if (!csvReaderService.isValidCSVFile(filePath)) {
            return ResponseEntity.badRequest().build();
        }

        String filename = downloadFilename != null ? downloadFilename : "download.csv";
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(csvReaderService.streamCSVToResponse(filePath));
    }
}
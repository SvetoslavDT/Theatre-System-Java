package bg.uni.fmi.theatre.web;

import bg.uni.fmi.theatre.dto.PerformanceResponse;
import bg.uni.fmi.theatre.service.PerformanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/performances")
public class PerformanceController {

    PerformanceService performanceService;

    public PerformanceController(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    @GetMapping
    @Operation(summary = "List performances", description = "List all performances, optionally filtered by show ID")
    @ApiResponse(responseCode = "200", description = "List of performances")
    @ApiResponse(responseCode = "404", description = "Show not found (when showId is provided but invalid)")
    public List<PerformanceResponse> listPerformances(
        @Parameter(description = "Filter by show ID")
        @RequestParam(required = false) Long showID) {

        return showID != null ? performanceService.findPerformancesByShow(showID) :
            performanceService.getAllPerformances();
    }
}

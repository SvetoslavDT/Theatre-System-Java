package bg.uni.fmi.theatre.web;

import bg.uni.fmi.theatre.service.PerformanceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/performances")
public class PerformanceController {

    PerformanceService performanceService;

    public PerformanceController(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }
}

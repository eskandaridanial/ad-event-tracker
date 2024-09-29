package com.coding.interview.controller;

import com.coding.interview.model.AdvertiserPerformance;
import com.coding.interview.model.Metric;
import com.coding.interview.services.EventProcessingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "${server.path}")
public class EventController {

    private final EventProcessingService eventProcessingService;

    public EventController(EventProcessingService eventProcessingService) {
        this.eventProcessingService = eventProcessingService;
    }

    @GetMapping("/metrics")
    public List<Metric> getMetrics() {
        return eventProcessingService.getMetrics();
    }

    @GetMapping("/performance")
    public List<AdvertiserPerformance> getPerformanceMetrics() {
        return eventProcessingService.getPerformanceMetrics();
    }
}

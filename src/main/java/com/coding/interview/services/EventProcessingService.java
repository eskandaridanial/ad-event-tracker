package com.coding.interview.services;

import com.coding.interview.model.AdvertiserPerformance;
import com.coding.interview.model.Click;
import com.coding.interview.model.Impression;
import com.coding.interview.model.Metric;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventProcessingService {

    private final EventCacheService eventCacheService;

    public EventProcessingService(EventCacheService eventCacheService) {
        this.eventCacheService = eventCacheService;
    }

    public List<Metric> getMetrics() {
        Map<String, Metric> metrics = new HashMap<>();
        Map<String, Metric> impressionToMetric = new HashMap<>();

        List<Impression> impressions = eventCacheService.getImpressions();
        List<Click> clicks = eventCacheService.getClicks();

        impressions.forEach(impression -> {
            String key = impression.getAppId() + "_" + impression.getCountryCode();

            Metric metric = metrics.computeIfAbsent(key, k -> {
                Metric m = new Metric();
                m.setAppId(impression.getAppId());
                m.setCountryCode(impression.getCountryCode());
                return m;
            });

            metric.setImpressions(metric.getImpressions() + 1);
            impressionToMetric.put(impression.getId(), metric);
        });

        clicks.forEach(click -> {
            Metric metric = impressionToMetric.get(click.getImpressionId());
            if (metric != null) {
                metric.setClicks(metric.getClicks() + 1);
                metric.setRevenue(metric.getRevenue() + click.getRevenue());
            }
        });

        return new ArrayList<>(metrics.values());
    }

    public List<AdvertiserPerformance> getPerformanceMetrics() {
        List<Click> clicks = eventCacheService.getClicks();
        List<Impression> impressions = eventCacheService.getImpressions();

        Map<String, Impression> impressionMap = impressions.stream()
                .collect(Collectors.toMap(
                        Impression::getId,
                        impression -> impression,
                        (existing, replacement) -> existing
                ));

        Map<String, Map<Integer, Double>> groupedRevenue = clicks.stream()
                .filter(click -> impressionMap.containsKey(click.getImpressionId()))
                .collect(Collectors.groupingBy(
                        click -> {
                            Impression impression = impressionMap.get(click.getImpressionId());
                            return impression.getAppId() + "_" + impression.getCountryCode();
                        },
                        Collectors.groupingBy(
                                click -> impressionMap.get(click.getImpressionId()).getAdvertiserId(),
                                Collectors.summingDouble(Click::getRevenue)
                        )
                ));

        return groupedRevenue.entrySet().stream()
                .map(entry -> {
                    String[] keys = entry.getKey().split("_");
                    int appId = Integer.parseInt(keys[0]);
                    String countryCode = keys[1];

                    List<Integer> top5Advertisers = entry.getValue().entrySet().stream()
                            .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                            .limit(5)
                            .map(Map.Entry::getKey)
                            .collect(Collectors.toList());

                    return new AdvertiserPerformance(appId, countryCode, top5Advertisers);
                })
                .toList();
    }
}

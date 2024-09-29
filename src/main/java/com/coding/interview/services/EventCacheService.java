package com.coding.interview.services;

import com.coding.interview.model.Click;
import com.coding.interview.model.Impression;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventCacheService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${path.to.impression.file}")
    private String impressionPath;
    @Value("${path.to.click.file}")
    private String clickPath;

    @Getter
    private List<Impression> impressions;
    @Getter
    private List<Click> clicks;

    @PostConstruct
    public void loadOnMemory() throws IOException {
        try (InputStream impressionStream = getClass().getClassLoader().getResourceAsStream(impressionPath)) {
            if (impressionStream == null) {
                throw new IOException("Impression file not found: " + impressionPath);
            }
            impressions = objectMapper.readValue(impressionStream, new TypeReference<>() {});

            impressions = impressions.stream()
                    .filter(impression -> !ObjectUtils.isEmpty(impression.getCountryCode()))
                    .collect(Collectors.toList());
        }

        try (InputStream clickStream = getClass().getClassLoader().getResourceAsStream(clickPath)) {
            if (clickStream == null) {
                throw new IOException("Click file not found: " + clickPath);
            }
            clicks = objectMapper.readValue(clickStream, new TypeReference<>() {});
        }
    }

}

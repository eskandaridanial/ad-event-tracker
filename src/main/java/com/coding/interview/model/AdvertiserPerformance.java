package com.coding.interview.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertiserPerformance {

    @JsonProperty("app_id")
    private int appId;
    @JsonProperty("country_code")
    private String countryCode;
    @JsonProperty("recommended_advertiser_ids")
    private List<Integer> recommendedAdvertiserIds;
}

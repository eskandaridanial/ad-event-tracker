package com.coding.interview.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Metric {
    @JsonProperty("app_id")
    private int appId;
    @JsonProperty("country_code")
    private String countryCode;
    private int impressions;
    private int clicks;
    private double revenue;
}

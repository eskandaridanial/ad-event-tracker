package com.coding.interview.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Impression {
    private String id;
    @JsonProperty("app_id")
    private int appId;
    @JsonProperty("advertiser_id")
    private int advertiserId;
    @JsonProperty("country_code")
    private String countryCode;
}

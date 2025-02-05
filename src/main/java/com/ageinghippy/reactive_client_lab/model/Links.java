package com.ageinghippy.reactive_client_lab.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Links {
    private String self;
    private String html;
    private String download;
    @JsonProperty("download_location")
    private String downloadLocation;
}

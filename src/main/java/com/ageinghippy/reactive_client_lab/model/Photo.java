package com.ageinghippy.reactive_client_lab.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Photo {

    private String id;
    private String slug;
    private Integer width;
    private Integer height;
    @JsonAlias("alt") //for Pexels
    private String description;
    @JsonAlias("src") //for Pexels
    private Urls urls;
    private Links links;
    @JsonProperty("asset_type")
    private String assetType;
}

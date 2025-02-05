package com.ageinghippy.reactive_client_lab.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UnsplashResponse {
    private Integer total;
    @JsonProperty("total_pages")
    private Integer totalPages;
    private List<Photo> results;

}

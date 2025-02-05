package com.ageinghippy.reactive_client_lab.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PexelsResponse {

    @JsonProperty("total_results")
    private Integer totalResults;
    private Integer page;
    @JsonProperty("per_page")
    private Integer perPage;
    private List<Photo> photos;


    public Integer getTotalPages() {
        Integer totalPages = ((Integer) totalResults / perPage) + (totalResults % perPage == 0 ? 0 : 1);
        return totalPages;
    }
}

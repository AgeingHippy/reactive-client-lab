package com.ageinghippy.reactive_client_lab.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class Urls {
    private String raw;
    private String full;
    private String regular;
    private String small;
    @JsonAlias("tiny") //for Pexels
    private String thumb;
}

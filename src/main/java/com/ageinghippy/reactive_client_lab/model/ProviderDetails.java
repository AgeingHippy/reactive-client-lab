package com.ageinghippy.reactive_client_lab.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProviderDetails {
    private String name;
    private String url;
}

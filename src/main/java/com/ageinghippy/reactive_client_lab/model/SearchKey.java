package com.ageinghippy.reactive_client_lab.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchKey {

    private String provider;
    private String text;
    private String orientation;

    public String getOrientation() {
        String response;
        if ("pexels".equals(provider) && "squarish".equals(orientation)) {
            return "square";
        }
        return orientation;
    }
}

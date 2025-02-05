package com.ageinghippy.reactive_client_lab.service;

import com.ageinghippy.reactive_client_lab.model.Photo;
import com.ageinghippy.reactive_client_lab.model.ProviderDetails;
import com.ageinghippy.reactive_client_lab.model.SearchKey;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SearchService {

    public Flux<Photo> getPhotos(SearchKey searchKey);

    public Mono<Integer> getTotalPages(SearchKey searchKey);

    public ProviderDetails getProviderDetails();
}

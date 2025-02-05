package com.ageinghippy.reactive_client_lab.service;

import com.ageinghippy.reactive_client_lab.model.Photo;
import com.ageinghippy.reactive_client_lab.model.ProviderDetails;
import com.ageinghippy.reactive_client_lab.model.SearchKey;
import com.ageinghippy.reactive_client_lab.model.UnsplashResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class UnsplashService implements SearchService {

    @Autowired
    WebClient unsplashWebClient;

    public Flux<Photo> getPhotos(SearchKey searchKey) {
        return getTotalPages(searchKey)
                .flatMapMany(t -> Flux.range(1, t > 5 ? 5 : t))
                .flatMap(f -> searchUnsplash(searchKey, f)
                        .flatMapIterable(UnsplashResponse::getResults), 5)
                .defaultIfEmpty(Photo.builder().description("No images returned").build());
    }

    public Mono<Integer> getTotalPages(SearchKey searchKey) {
        return unsplashWebClient.get()
                .uri(uri -> uri
                        .queryParam("page", "1")
                        .queryParam("query", searchKey.getText())
                        .queryParamIfPresent("orientation", Optional.ofNullable(searchKey.getOrientation()))
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(UnsplashResponse.class)
                .map(UnsplashResponse::getTotalPages)
                .map(Integer::valueOf); //this line is redundant as Mono<Integer> is already emitted by the previous line.
    }

    private Mono<UnsplashResponse> searchUnsplash(SearchKey searchKey, int pageNumber) {
        return unsplashWebClient.get()
                .uri(uri -> uri
                        .queryParam("page", pageNumber)
                        .queryParam("query", searchKey.getText())
                        .queryParamIfPresent("orientation", Optional.ofNullable(searchKey.getOrientation()))
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UnsplashResponse.class);
    }

    @Override
    public ProviderDetails getProviderDetails() {
        return ProviderDetails.builder().name("Unsplash").url("https://unsplash.com/").build();
    }
}


package com.ageinghippy.reactive_client_lab.service;

import com.ageinghippy.reactive_client_lab.model.PexelsResponse;
import com.ageinghippy.reactive_client_lab.model.Photo;
import com.ageinghippy.reactive_client_lab.model.ProviderDetails;
import com.ageinghippy.reactive_client_lab.model.SearchKey;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PexelsService implements SearchService {

    private final WebClient pexelsWebClient;

    @Override
    public Flux<Photo> getPhotos(SearchKey searchKey) {
        return getTotalPages(searchKey)
                .flatMapMany(t -> Flux.range(1, t > 5 ? 5 : t))
                .flatMap(f -> searchPexels(searchKey, f)
                        .flatMapIterable(PexelsResponse::getPhotos), 5)
                .defaultIfEmpty(Photo.builder().description("No images returned").build());
    }

    @Override
    public Mono<Integer> getTotalPages(SearchKey searchKey) {
        return pexelsWebClient.get()
                .uri(uri -> uri
                        .queryParam("page", "1")
                        .queryParam("query", searchKey.getText())
                        .queryParamIfPresent("orientation", Optional.ofNullable(searchKey.getOrientation()))
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().bodyToMono(PexelsResponse.class)
                .map(PexelsResponse::getTotalPages)
                .map(Integer::valueOf); //this line is redundant as Mono<Integer> is already emitted by the previous line.
    }

    private Mono<PexelsResponse> searchPexels(SearchKey searchKey, int pageNumber) {
        return pexelsWebClient.get()
                .uri(uri -> uri
                        .queryParam("page", pageNumber)
                        .queryParam("query", searchKey.getText())
                        .queryParamIfPresent("orientation", Optional.ofNullable(searchKey.getOrientation()))
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PexelsResponse.class);
    }

    @Override
    public ProviderDetails getProviderDetails() {
        return ProviderDetails.builder().name("Pexels").url("https://www.pexels.com").build();
    }
}

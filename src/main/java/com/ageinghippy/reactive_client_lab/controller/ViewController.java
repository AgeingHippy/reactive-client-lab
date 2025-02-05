package com.ageinghippy.reactive_client_lab.controller;

import com.ageinghippy.reactive_client_lab.model.SearchKey;
import com.ageinghippy.reactive_client_lab.service.PexelsService;
import com.ageinghippy.reactive_client_lab.service.SearchService;
import com.ageinghippy.reactive_client_lab.service.UnsplashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;

@Controller
public class ViewController {

    @Autowired
    UnsplashService unsplashService;
    @Autowired
    PexelsService pexelsService;

    @GetMapping("/")
    public String displayIndex(Model model) {
        model.addAttribute("searchKey", new SearchKey());
        return "index";
    }

    @PostMapping("/")
    public String performSearch(
            @ModelAttribute("searchKey") SearchKey searchKey,
            Model model) {

        //todo - I suspect using a factory method here might be better. To investigate factories...
        SearchService searchService = searchKey.getProvider().equals("unsplash") ? unsplashService : pexelsService;

        ReactiveDataDriverContextVariable reactiveData =
                new ReactiveDataDriverContextVariable(
                        searchService.getPhotos(searchKey),
                        1);
        model.addAttribute("photos", reactiveData);
        model.addAttribute("searchText", searchKey.getText());
        model.addAttribute("providerDetails", searchService.getProviderDetails());
        return "index";
    }
}

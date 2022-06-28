package com.damnj.hello.controller;

import com.damnj.hello.entity.Language;
import com.damnj.hello.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloViewController {

    private LanguageRepository repository;

    @Autowired
    public HelloViewController(LanguageRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/hello")
    public String hello(@RequestParam(name = "language", defaultValue = "EN") String language, Model model){
        Language languageEntity = this.repository.findById( language ).get();

        System.out.println( languageEntity.getTranslation() );;
        model.addAttribute( "language", languageEntity.getTranslation() );
        return "hello";
    }
    @GetMapping("/secure/hello")
    public String secureHello(@RequestParam(name = "language", defaultValue = "EN") String language, Model model){
        Language languageEntity = this.repository.findById( language.toUpperCase() ).get();

        System.out.println( languageEntity.getTranslation() );;
        model.addAttribute( "language", languageEntity.getTranslation() );
        return "hello";
    }
}

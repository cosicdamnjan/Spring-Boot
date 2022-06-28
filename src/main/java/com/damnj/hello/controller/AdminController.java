package com.damnj.hello.controller;

import com.damnj.hello.entity.Language;
import com.damnj.hello.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

    private LanguageRepository repository;

    @Autowired
    public AdminController(LanguageRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/admin")
    public ModelAndView showAdminPage(ModelAndView model, Language language){
        model.setViewName( "admin" );
        model.addObject( "adminLanguageForm", language );
        return model;
    }

    @PostMapping("/add-language")
    public String addLangugage(Model model, @Validated Language  adminLanguageForm){
        model.addAttribute( "adminLanguageForm", adminLanguageForm );
        try{
            this.repository.save( adminLanguageForm );
            model.addAttribute( "message", "Language and message added successfully" );
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute( "message", "Failed to add the language" );
        }

        return "admin";
    }
}

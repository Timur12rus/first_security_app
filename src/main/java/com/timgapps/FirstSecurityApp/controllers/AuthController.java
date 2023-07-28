package com.timgapps.FirstSecurityApp.controllers;

import com.timgapps.FirstSecurityApp.models.Person;
import com.timgapps.FirstSecurityApp.services.RegistrtionService;
import com.timgapps.FirstSecurityApp.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private RegistrtionService registrtionService;
    private final PersonValidator personValidator;

    @Autowired
    public AuthController(RegistrtionService registrtionService, PersonValidator personValidator) {
        this.registrtionService = registrtionService;
        this.personValidator = personValidator;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "auth/registration";
    }

    // используем аннотацию @Valid для валидации человека, потому что есть аннотации в модели над полями для валидации
    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        // дополнительно валидируем человека на то, что человека с таким именем больше нет в БД
        // помещаем ошибку,если она есть
        personValidator.validate(person, bindingResult);

        // если есть ошибки, обратно перенаправляем этого человека на регистрацию
        if (bindingResult.hasErrors()) {
            return "/auth/registration";
        }
        registrtionService.register(person);

        return "redirect:/auth/login";
    }
}

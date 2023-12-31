package com.timgapps.FirstSecurityApp.controllers;

import com.timgapps.FirstSecurityApp.security.PersonDetails;
import com.timgapps.FirstSecurityApp.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    private final AdminService adminService;

    @Autowired
    public HelloController(AdminService adminService) {
        this.adminService = adminService;
    }


    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        // получаем доступ к объекту authentication:
        // с помощью спецального SecurityContextHolder'а обращаемся к контексту и на нем
        // получаем объект authentication, который был получен после успешной аутентификации
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        System.out.println(personDetails.getPerson());

        return "hello";
    }

    // чтобы разграничить доступ к частям нашего приложения используем аннотацию @PreAuthorize в методе сервиса
    // для этого мы должны создать страницу, на которую может попасть только администратор
    @GetMapping("/admin")
    public String adminPage() {
        adminService.doAdminStuff();
        return "admin";
    }

}

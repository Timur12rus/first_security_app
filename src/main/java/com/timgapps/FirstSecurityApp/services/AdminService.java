package com.timgapps.FirstSecurityApp.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

// создадим этот сервис для методоа контроллера HelloController, для метода adminPage()
@Service
public class AdminService {

    // должен выполнятся только администратором
    // используем для этого аннотацию @PreAuthorize
    @PreAuthorize("hasRole('ROLE_ADMIN')")  // здесь пишем условие при котором допускаем пользователя в этот метод
//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SOME_OTHER')")  // здесь пишем условие при котором допускаем пользователя в этот метод
//    @PreAuthorize("hasRole('ROLE_ADMIN') and hasRole('ROLE_SOME_OTHER')")  // здесь пишем условие при котором допускаем пользователя в этот метод
    public void doAdminStuff() {
        System.out.println("Only admin here");
    }
}

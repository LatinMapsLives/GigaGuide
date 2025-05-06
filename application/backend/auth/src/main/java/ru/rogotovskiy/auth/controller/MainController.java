package ru.rogotovskiy.auth.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Hidden
@RestController
public class MainController {

    @GetMapping("/unsecured")
    public String unsecuredData() {
        return "Unsecured Data!";
    }

    @GetMapping("/secured")
    public String securedData() {
        return "Secured Data!";
    }

    @GetMapping("/admin")
    public String adminData() {
        return "Admin Data!";
    }

    @GetMapping("/info")
    public String userData(Principal principal) {
        return principal.getName();
    }
}

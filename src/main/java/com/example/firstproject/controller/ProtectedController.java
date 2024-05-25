package com.example.firstproject.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/protected-endpoint")
public class ProtectedController {

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public String protectedEndpoint() {
        return "This is a protected endpoint!";
    }
}

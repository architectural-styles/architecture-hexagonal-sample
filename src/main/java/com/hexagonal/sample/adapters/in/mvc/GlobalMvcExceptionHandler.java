package com.hexagonal.sample.adapters.in.mvc;

import com.hexagonal.sample.core.domain.UserNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.hexagonal.sample.adapters.in.mvc")
public class GlobalMvcExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public String handleNotFound(UserNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "form/main-page-search";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleValidation(IllegalArgumentException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "form/main-page-search";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneric(Exception ex, Model model) {
        model.addAttribute("error", "Internal error: " + ex.getMessage());
        return "form/main-page-search";
    }
}

package com.testApi;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkApi")
public class UserSessionController {

    @GetMapping("/checkUserSession")
    public ResponseEntity<Boolean> checkUserSession(HttpSession session) {
        Boolean userExists = session.getAttribute("user") != null;
        return ResponseEntity.ok(userExists);
    }
}
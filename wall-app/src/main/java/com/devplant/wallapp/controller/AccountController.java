package com.devplant.wallapp.controller;

import com.devplant.wallapp.model.Account;
import com.devplant.wallapp.model.AccountRegistrationRequest;
import com.devplant.wallapp.model.ChangePasswordRequest;
import com.devplant.wallapp.model.LoginRequest;
import com.devplant.wallapp.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/accounts")
@Api(description = "Register Accounts & Change Passwords", tags = "accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    @ApiOperation(value = "Login in a JSON manner :)", nickname = "login")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Request Successful"),
            @ApiResponse(code = 401, message = "Unauthorized, invalid credentials") })
    public Account login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        return accountService.me();
    }

    @PostMapping("/logout")
    @ApiOperation(value = "Logout in a JSON manner :)", nickname = "logout")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Request Successful"),
            @ApiResponse(code = 401, message = "Unauthorized, not yet logged in :)") })
    public void logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(true);
        session.removeAttribute("SPRING_SECURITY_CONTEXT");
    }

    @GetMapping("/me")
    @ApiOperation(value = "Returns the current loggedin user", nickname = "me")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Request Successful"),
            @ApiResponse(code = 401, message = "Unauthorized, not logged in") })
    public Account me() {
        return accountService.me();
    }

    @PostMapping("/register")
    @ApiOperation(value = "Create a new Account", nickname = "register")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request Successful, Account has been created & can be used to login"),
            @ApiResponse(code = 400, message = "Account with that username already exists"),
            @ApiResponse(code = 412, message = "Password to short, min length = 6") })
    public void register(@RequestBody AccountRegistrationRequest request) {
        accountService.registerAccount(request);
    }

    @PostMapping("/change-password")
    @ApiOperation(value = "Change Password", nickname = "changePassword")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Request Successful, Password has been changed"),
            @ApiResponse(code = 401, message = "Unauthorized, not logged in"),
            @ApiResponse(code = 422, message = "Old password does not match stored value"),
            @ApiResponse(code = 412, message = "Password to short, min length = 6") })
    public void changePassword(@RequestBody ChangePasswordRequest request) {
        accountService.changePassword(request);
    }

}

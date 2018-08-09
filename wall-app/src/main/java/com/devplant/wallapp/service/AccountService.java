package com.devplant.wallapp.service;

import com.devplant.wallapp.exceptions.AccountAlreadyExistsException;
import com.devplant.wallapp.exceptions.PasswordDoesNotMatchException;
import com.devplant.wallapp.exceptions.UnauthorizedException;
import com.devplant.wallapp.model.Account;
import com.devplant.wallapp.model.AccountRegistrationRequest;
import com.devplant.wallapp.model.ChangePasswordRequest;
import com.devplant.wallapp.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collections;

@Service
@Validated
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account me() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            throw new UnauthorizedException("You are not authorized");
        }
        Account account = accountRepo.findOne(authentication.getName());
        if(account == null ){
            throw new UnauthorizedException("You are not authorized");
        }

        return account;
    }

    public void registerAccount(@Valid AccountRegistrationRequest request) {
        if (accountRepo.exists(request.getUsername())) {
            throw new AccountAlreadyExistsException(
                    "Account: " + request.getUsername() + " already registered!");
        }

        Account account = Account.builder()
                .enabled(true)
                .roles(Collections.singletonList("ROLE_USER"))
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        accountRepo.save(account);
    }

    @Transactional
    public void changePassword(@Valid ChangePasswordRequest request) {
        Account account = me();
        if (!passwordEncoder.matches(request.getOldPassword(),
                account.getPassword())) {
            throw new PasswordDoesNotMatchException(
                    "Old and current password do not match"
            );
        }

        account.setPassword(passwordEncoder.encode(
                request.getNewPassword()
        ));

    }

}

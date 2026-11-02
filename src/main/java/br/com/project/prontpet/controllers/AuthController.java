package br.com.project.prontpet.controllers;

import br.com.project.prontpet.dtos.AccountRequest;
import br.com.project.prontpet.dtos.AccountResponse;
import br.com.project.prontpet.models.Account;
import br.com.project.prontpet.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AccountService accountService;

    public AuthController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<AccountResponse> register(
            @RequestBody AccountRequest request
    ) {
        Account account = accountService.addAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountResponse.fromEntity(account));
    }
}

package org.example.financespro.controller;

import org.example.financespro.dto.request.AccountRequestDto;
import org.example.financespro.dto.response.AccountResponseDto;
import org.example.financespro.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/accounts")
public class AccountController {

  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @PostMapping
  public ResponseEntity<AccountResponseDto> createAccount(@Valid @RequestBody AccountRequestDto request) {
    return ResponseEntity.ok(accountService.createAccount(request));
  }

  @PostMapping("/details")
  public ResponseEntity<AccountResponseDto> getAccount(@Valid @RequestBody AccountRequestDto request) {
    // Assuming the `accountNumber` in the request is used to fetch the account details
    return ResponseEntity.ok(accountService.getAccountDetailsByNumber(request.getNumber()));
  }
}

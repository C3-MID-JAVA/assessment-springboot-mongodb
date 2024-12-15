package org.example.financespro.controller;

import org.example.financespro.dto.request.AccountRequestDto;
import org.example.financespro.dto.response.AccountResponseDto;
import org.example.financespro.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing accounts.
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {

  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @PostMapping
  public ResponseEntity<AccountResponseDto> createAccount(
      @Validated @RequestBody AccountRequestDto request) {
    return ResponseEntity.ok(accountService.createAccount(request));
  }

  /**
   * Retrieves an account by its ID.
   *
   * @param id the ID of the account to retrieve
   * @return the account details
   */
  @GetMapping("/{id}")
  public ResponseEntity<AccountResponseDto> getAccount(@PathVariable Long id) {
    return ResponseEntity.ok(accountService.getAccountById(id));
  }
}

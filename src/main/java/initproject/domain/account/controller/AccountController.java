package initproject.domain.account.controller;

import initproject.domain.account.entity.Account;
import initproject.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public String signUp(@RequestBody Account account) {

        accountService.signUp(account);

        return "success signUp";
    }
}

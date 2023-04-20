package com.example.account.service;

import com.example.account.domain.Account;
import com.example.account.domain.AccountUser;
import com.example.account.dto.AccountDto;
import com.example.account.exception.AccountException;
import com.example.account.repository.AccountRepository;
import com.example.account.repository.AccountUserRepository;
import com.example.account.type.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.account.type.AccountStatus.IN_USE;

@Service
@RequiredArgsConstructor
public class AccountService { // Service -> Repository
    private final AccountRepository accountRepository;
    private final AccountUserRepository accountUserRepository;

    /**
     * 사용자가 존재하는지 확인
     * 계좌번호 생성
     * 계좌번호 저장, 전송
     */
    @Transactional
    public AccountDto createAccount(Long userId, Long initialBalance) {
        AccountUser accountUser = accountUserRepository.findById(userId)
                .orElseThrow(() -> new AccountException(ErrorCode.USER_NOT_FOUND));
        String newAccountNumber = accountRepository.findFirstByOrOrderByIdDesc()
                .map(account -> Integer.parseInt(account.getAccountNumber()) + 1 + "")
                .orElse("1000000000");


        return AccountDto.fromEntity(accountRepository.save(Account
                .builder()
                .accountUser(accountUser)
                .accountStatus(IN_USE)
                .accountNumber(newAccountNumber)
                .balance(initialBalance)
                .registeredAt(LocalDateTime.now())
                .build()));

    }

    @Transactional
    public Account getAccount(Long id) {
        return accountRepository.findById(id).get();
    }

}

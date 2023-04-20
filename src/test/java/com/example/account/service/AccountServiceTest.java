package com.example.account.service;

import com.example.account.domain.Account;
import com.example.account.type.AccountStatus;
import com.example.account.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @InjectMocks
    private AccountService accountService;

    @Test
    @DisplayName("계좌 조회 성공")
    void getAccountTest() {
        //given
        given(accountRepository.findById(anyLong()))
                .willReturn(Optional.of(Account
                        .builder()
                        .accountStatus(AccountStatus.UNREGISTERED)
                        .accountNumber("6379")
                        .build()));
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        //when
        Account account = accountService.getAccount(45678L);
        //then
        verify(accountRepository,times(1)).findById(captor.capture());
        verify(accountRepository,times(0)).save(any());
        assertEquals(45678L,captor.getValue());
        assertEquals("6379",account.getAccountNumber());
        assertEquals(AccountStatus.UNREGISTERED,account.getAccountStatus());
    }

}
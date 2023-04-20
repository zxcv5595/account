package com.example.account.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.apache.coyote.Request;

import java.time.LocalDateTime;

public class CreateAccount {
    @Getter
    @Setter
    public static class Request {
        @NotNull
        @Min(1)
        private Long userId;
        @NotNull
        @Min(100)
        private Long initialBanlance;

    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long userId;
        private String accountNumber;
        private LocalDateTime registeredAt;

    }
}

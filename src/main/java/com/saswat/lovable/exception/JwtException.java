package com.saswat.lovable.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtException extends RuntimeException {
    private final String message;
}

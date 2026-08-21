package com.saswat.lovable.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Setter
@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserContext {
    private Long userId;
}

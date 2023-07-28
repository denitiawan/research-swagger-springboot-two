package com.deni.app.common.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditAwareImpl implements AuditorAware<String> {

    // untuk mendapatkan siapa yang sedang login
    @Override
    public Optional<String> getCurrentAuditor() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.of(username);
    }
}

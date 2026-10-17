package com.gts.backcommons.jwtauth;

import com.gts.backcommons.enums.EntityStatus;
import com.gts.backcommons.exceptions.InactiveUserException;
import org.springframework.security.core.userdetails.UserDetails;

public final class UserAccessPolicy {
    private UserAccessPolicy() {}

    public static boolean isActive(String status) {
        return EntityStatus.ACTIF.name().equals(status);
    }

    public static void requireActive(UserDetails user) {
        if (user == null || !user.isEnabled()) {
            throw new InactiveUserException();
        }
    }
}

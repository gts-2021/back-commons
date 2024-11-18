package com.gts.backcommons.ssi.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonUserConstants {

    // user validation constants

    public final static String FAMILY_NAME_ERROR = "family name cannot be blank";
    public final static String FIRST_NAME_ERROR = "first name cannot be blank";
    public final static String PSEUDO_ERROR = "pseudo  cannot be blank";
    public final static String PASSWORD_ERROR = "password  cannot be blank";
    public final static String ROLE_ERROR = "role cannot be null";

    // default user constant
    public static final String DEFAULT_USER_NAME = "ADMIN";
    public static final String DEFAULT_USER_PSEUDO= "admin";
    public static final String DEFAULT_ADMIN_PASSWORD = "admin";

    public static final String USER_NOT_FOUND= "user not found";

    public static final String PSEUDO_REQUIRED = "pseudo is required";
    public static final String PASSWORD_REQUIRED = "password is required";
    public static final String COMPANY_CODE_REQUIRED = "company code is required";
}

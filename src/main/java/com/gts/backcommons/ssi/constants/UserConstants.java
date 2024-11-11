package com.gts.backcommons.ssi.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserConstants {

    // user validation constants
    public final static String COMPANY_CODE_ERROR = "company code cannot be blank";
    public final static String FAMILY_NAME_ERROR = "family name cannot be blank";
    public final static String FIRST_NAME_ERROR = "first name cannot be blank";
    public final static String PSEUDO_ERROR = "pseudo  cannot be blank";
    public final static String PASSWORD_ERROR = "password  cannot be blank";
    public final static String ROLE_ERROR = "role cannot be null";

    // default user constant
    public static final String DEFAULT_USER_NAME = "ADMIN";
    public static final String DEFAULT_USER_PSEUDO= "admin";

    public static final String USER_NOT_FOUND= "user not found";
}

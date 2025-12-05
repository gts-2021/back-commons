package com.gts.backcommons.jwtauth;

public class Constants {

    public static Long TOKEN_VALIDITY= 60* 1000L; // 15min 15 * 60* 1000L
    public static Long REFRESH_TOKEN_VALIDITY= 3 * 60 * 1000L; // 7days 7 * 24 * 60 * 60 * 1000L
    public static String HEADER_STRING="Authorization";
    public static String TOKEN_PREFIX="Bearer ";
    public static String ACCESS_TOKEN="accessToken";
    public static final String REFRESH_TOKEN_REQUIRED = "refresh token is required";
    //public static String HEADER_STRING;
}

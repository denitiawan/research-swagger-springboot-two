package com.deni.app.security.filter;

public class JwtConstants {
    public static final String TOKEN_SECRET= "SomeSecretForJWTGeneration";
    //public static final int TOKEN_EXPIRED_TIME = 32400; // 9 JAM
    public static final int TOKEN_EXPIRED_TIME = 864_000_000; // 10 days
    public static final String TOKEN_X_KEY = "Authorization";

    // OAuth0 (MD5 Hash) = c0bb6ebafe8c13d20028172c813dd2bb
    public static final String TOKEN_PREFIX = "c0bb6ebafe8c13d20028172c813dd2bb"+".";
    public static final String TOKEN_PREFIX_BEARER = "Bearer ";

}

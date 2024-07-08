package com.example.blog.global.security;

import java.util.List;

public class SecurityConst {
    public static final String BLOG_SID = "blogSid";
    public static final List<String> passInterceptorUrlPattern = List.of("@\\d+", "@\\d+/.+", "^/post(/\\\\d+)?$");

}

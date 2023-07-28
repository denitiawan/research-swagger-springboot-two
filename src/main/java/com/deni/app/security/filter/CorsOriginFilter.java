package com.deni.app.security.filter;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// this class for enable response CORS origin
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsOriginFilter implements Filter {

    @Value("${custom.app.allowedOriginsClientURL}")
    private String allowedOriginsClientURL;

    public CorsOriginFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    // this is for enable response CORS origin
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        response.setHeader("Access-Control-Allow-Origin", allowedOriginsClientURL);
        response.setHeader("Access-Control-Allow-Methods", "POST,PUT,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", String.valueOf(JwtConstants.TOKEN_EXPIRED_TIME));
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Authorization, X-AUTH, content-type");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }


    @Override
    public void destroy() {
    }
}



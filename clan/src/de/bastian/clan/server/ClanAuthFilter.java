package de.bastian.clan.server;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClanAuthFilter implements Filter {

    public static final String APPSESSIONID = "clan";

    private final static String loginRequired[] = {
        "edit"
    };

    private final static String adminRequired[] = {

    };

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//        boolean r = true;
//        String url = request.getServletPath();
//        System.out.println(url);
//
//        for (String s : loginRequired) {
//            if (url.indexOf(s) != -1) {
//                r = (User.isLoggedIn() != null);
//                break;
//            }
//        }
//
//        for (String s : adminRequired) {
//            if (url.indexOf(s) != -1) {
//                r = (User.isLoggedIn() != null && User.isLoggedIn().getType().equals(UserProxy.Type.Admin.name()));
//                break;
//            }
//        }
//
//        if (!r) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {}

}

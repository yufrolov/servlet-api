package com.yufrolov.vocabulary.filter;

import com.yufrolov.vocabulary.entity.Profile;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/vocabulary/*")
public class AuthenticationFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        var session = req.getSession(true);
        Profile profile = (Profile) session.getAttribute("profile");
        if (profile == null) {
            res.sendRedirect(req.getContextPath() + "/login?authenticated=false");
            return;
        }
        chain.doFilter(req, res);
    }
}

package com.yufrolov.vocabulary.controller;

import com.yufrolov.vocabulary.entity.Profile;
import com.yufrolov.vocabulary.repository.ProfileRepository;
import com.yufrolov.vocabulary.service.ProfileService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private final ProfileService profileService;

    public LoginController() {
        this.profileService = new ProfileService(new ProfileRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String target = null;
        if (req.getSession(true).getAttribute("profile") != null) {
            target = "/vocabulary/list";
        } else {
            target = "/login.jsp";
        }

        var requestDispatcher = getServletContext()
                .getRequestDispatcher(target);
        requestDispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var login = req.getParameter("login");
        var password = req.getParameter("password");
        Profile profile = profileService.doLogin(login, password);
        if (profile != null) {
            req.getSession(true).setAttribute("profile", profile);
            resp.sendRedirect(req.getContextPath() + "/vocabulary/list");
        } else {
            resp.sendRedirect(req.getContextPath() + "/login?authenticated=false");
        }
    }
}

package com.yufrolov.vocabulary.controller;

import com.yufrolov.vocabulary.entity.Profile;
import com.yufrolov.vocabulary.repository.ProfileRepository;
import com.yufrolov.vocabulary.service.ProfileService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/signup")
public class SignUpController extends HttpServlet {

    private final ProfileService profileService;

    public SignUpController() {
        this.profileService = new ProfileService(new ProfileRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession(true);
        Profile profile = (Profile) session.getAttribute("profile");
        if (profile != null) {
            session.removeAttribute("profile");
        }
        String target = "/signup.jsp";

        var requestDispatcher = getServletContext()
                .getRequestDispatcher(target);
        requestDispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var login = req.getParameter("login");
        var password = req.getParameter("password");
        var fio = req.getParameter("fio");
        String target = null;
        if (profileService.doSignUp(fio, login, password)) {
            target = "/login.jsp";
        } else {
            target = "/signup.jsp";
        }
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(target);
        requestDispatcher.forward(req, resp);
    }
}

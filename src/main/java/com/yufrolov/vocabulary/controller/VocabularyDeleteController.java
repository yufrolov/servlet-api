package com.yufrolov.vocabulary.controller;

import com.yufrolov.vocabulary.entity.Profile;
import com.yufrolov.vocabulary.repository.VocabularyRepository;
import com.yufrolov.vocabulary.service.VocabularyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/vocabulary/delete")
public class VocabularyDeleteController extends HttpServlet {

    private final VocabularyService vocabularyService;

    public VocabularyDeleteController() {
        this.vocabularyService = new VocabularyService(new VocabularyRepository());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession(true);
        Profile profile = (Profile) session.getAttribute("profile");
        Long id = Long.valueOf(req.getParameter("id"));
        vocabularyService.delete(profile.id(), id);
        String target = "/vocabulary/list";
        resp.sendRedirect(req.getContextPath() + target);
    }
}

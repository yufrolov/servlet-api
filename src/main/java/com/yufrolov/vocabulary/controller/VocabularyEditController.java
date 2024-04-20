package com.yufrolov.vocabulary.controller;

import com.yufrolov.vocabulary.dto.VocabularyDTO;
import com.yufrolov.vocabulary.entity.Profile;
import com.yufrolov.vocabulary.repository.VocabularyRepository;
import com.yufrolov.vocabulary.service.VocabularyService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/vocabulary/edit")
public class VocabularyEditController extends HttpServlet {
    private final VocabularyService vocabularyService;

    public VocabularyEditController() {
        this.vocabularyService = new VocabularyService(new VocabularyRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession(true);
        Profile profile = (Profile) session.getAttribute("profile");
        String sId = req.getParameter("id");
        VocabularyDTO vocabulary = null;
        if (sId != null) {
            Long id = Long.valueOf(sId);
            vocabulary = vocabularyService.getVocabularyDTO(profile.id(), id);
        }

        if (vocabulary == null) {
            resp.sendRedirect(req.getContextPath() + "/vocabulary/list");
            return;
        }
        req.setAttribute("vocabulary", vocabulary);
        getServletContext().getRequestDispatcher("/vocabulary/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession(true);
        Profile profile = (Profile) session.getAttribute("profile");
        String word = req.getParameter("word");
        String translateWord = req.getParameter("translate_word");
        Long id = Long.valueOf(req.getParameter("id"));
        String target = null;
        if (vocabularyService.update(profile.id(), word, translateWord, id)) {
            target = "/vocabulary/list";
        } else {
            target = "/vocabulary/edit?order_number=" + id;
        }
//        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(target);
//        requestDispatcher.forward(req,resp);
        resp.sendRedirect(req.getContextPath() + target);
    }
}

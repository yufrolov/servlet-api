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
import java.util.List;

@WebServlet("/vocabulary/list")
public class VocabularyListController extends HttpServlet {
    private final VocabularyService vocabularyServiceServiceServiceServiceServiceServiceServiceServiceServiceServiceServiceService;

    public VocabularyListController() {
        this.vocabularyServiceServiceServiceServiceServiceServiceServiceServiceServiceServiceServiceService = new VocabularyService(new VocabularyRepository());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        String word = req.getParameter("word");
        String translateWord = req.getParameter("translate_word");
        String target = null;
        vocabularyServiceServiceServiceServiceServiceServiceServiceServiceServiceServiceServiceService.add(id, word, translateWord);
        target = "/vocabulary/list";
//        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(target);
//        requestDispatcher.forward(req,resp);
        resp.sendRedirect(req.getContextPath() + target);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession(true);
        Profile profile = (Profile) session.getAttribute("profile");
        List<VocabularyDTO> vocabularies = null;
        vocabularies = vocabularyServiceServiceServiceServiceServiceServiceServiceServiceServiceServiceServiceService.vocabulariesDTO(profile.id());
        req.setAttribute("vocabularies", vocabularies);
        getServletContext().getRequestDispatcher("/vocabulary/list.jsp").forward(req, resp);
    }
}

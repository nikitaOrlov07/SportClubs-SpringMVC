package com.example.web.controller;

import com.example.web.dto.ClubDto;
import com.example.web.models.Club;
import com.example.web.models.Comment;
import com.example.web.security.SecurityUtil;
import com.example.web.service.ClubService;
import com.example.web.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    private ClubService clubService;

    @PostMapping("/comments/{clubId}/save")
    public String addComment(@ModelAttribute("comments") Comment comment , @PathVariable("clubId") Long clubId) {
        String username = SecurityUtil.getSessionUser();
        comment.setId(clubId);

        comment.setAuthor(username);
        commentService.saveComment(comment);
        return "redirect:/clubs/"+clubId;
    }
}

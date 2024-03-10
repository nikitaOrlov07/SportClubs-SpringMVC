package com.example.web.service;

import com.example.web.models.Comment;

import java.util.List;

public interface CommentService {
     Comment saveComment(Comment comment);

    List<Comment> getComments(Long clubId);
}

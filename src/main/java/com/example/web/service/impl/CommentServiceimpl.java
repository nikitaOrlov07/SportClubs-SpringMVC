package com.example.web.service.impl;

import com.example.web.models.Comment;
import com.example.web.repository.CommentRepository;
import com.example.web.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceimpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getComments(Long clubId) {
        return commentRepository.findCommentByClubId(clubId);
    }
}

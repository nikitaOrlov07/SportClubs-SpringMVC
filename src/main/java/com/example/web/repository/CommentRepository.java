package com.example.web.repository;

import com.example.web.models.Comment;
import com.example.web.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
List<Comment> findCommentByClubId(Long clubId);

}

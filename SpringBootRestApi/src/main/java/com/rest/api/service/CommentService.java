package com.rest.api.service;

import java.util.List;
import java.util.Optional;

import com.rest.api.entity.Comment;
import com.rest.api.entity.dto.CommentDTO;

public interface CommentService  {
    List<CommentDTO> getAll();

    Optional<CommentDTO> findById(Long id);

    CommentDTO save(CommentDTO dto);
}

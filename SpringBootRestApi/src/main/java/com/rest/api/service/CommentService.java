package com.rest.api.service;

import java.util.List;
import java.util.Optional;

import com.rest.api.utils.request.CommentDTO;
import com.rest.api.utils.response.CommentResponseDTO;

public interface CommentService  {
    List<CommentResponseDTO> getAll();

    Optional<CommentResponseDTO> findById(Long id);

    CommentResponseDTO save(CommentDTO dto);
}

package com.rest.api.service.impl;

import com.rest.api.entity.Comment;
import com.rest.api.entity.dto.CommentDTO;
import com.rest.api.repository.CommentRepository;
import com.rest.api.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

//    public CommentServiceImpl(CommentRepository commentRepository) {
//        this.commentRepository = commentRepository;
//    }


    @Override
    public List<CommentDTO> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public Optional<CommentDTO> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public CommentDTO save(CommentDTO dto) {
        Comment cmt = new Comment();
        cmt.setName(dto.getName());
        cmt.setEmail(dto.getEmail());
        cmt.setBody(dto.getBody());
       // cmt.setPost(dto.getPost());
        return commentRepository.save(cmt);
    }

    public CommentDTO mapperToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setBody(comment.getBody);
        dto.setEmail(comment.getEmail);

    }


}

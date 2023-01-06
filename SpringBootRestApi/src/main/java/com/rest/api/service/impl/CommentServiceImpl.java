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
//    }//todo 18:24 initialise??? lombok dont work


    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Comment save(CommentDTO dto) {
        Comment cmt = new Comment();
        cmt.setName(dto.getName());
        cmt.setEmail(dto.getEmail());
        cmt.setBody(dto.getBody());
        cmt.setPost(dto.getPost());
        return commentRepository.save(cmt);//todo the end lombok ass MF 20:33
    }
}

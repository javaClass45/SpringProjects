package com.rest.api.controller;

import com.rest.api.entity.Comment;
import com.rest.api.utils.request.CommentDTO;
import com.rest.api.service.CommentService;
import com.rest.api.utils.response.CommentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/getAll")
    public ResponseEntity<List<CommentResponseDTO>> getAllComment() {
        return new ResponseEntity<>(commentService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CommentResponseDTO> createComment(@RequestBody CommentDTO dto) {
        return new ResponseEntity<>(commentService.save(dto), HttpStatus.CREATED);
    }
}


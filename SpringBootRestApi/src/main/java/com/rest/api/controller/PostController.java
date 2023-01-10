package com.rest.api.controller;

import com.rest.api.service.PostService;
import com.rest.api.utils.request.PostDTO;
import com.rest.api.utils.response.PostResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/findall")
    public ResponseEntity<Object> findAll() {
        return new ResponseEntity<>(postService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<PostResponseDTO> create(@RequestBody PostDTO dto) {
        return new ResponseEntity<>(postService.save(dto), HttpStatus.OK);

    }


    @PutMapping("/update/{id}")
    public ResponseEntity<PostResponseDTO> update(@PathVariable("id") Long id,
                                         @RequestBody PostDTO dto) {
        return new ResponseEntity<>(postService.update(dto, id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        postService.delete(id);
        return "Deleted success!";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(postService.findById(id), HttpStatus.OK);

    }



}

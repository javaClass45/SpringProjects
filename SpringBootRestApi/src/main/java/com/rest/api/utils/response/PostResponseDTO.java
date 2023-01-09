package com.rest.api.utils.response;

import com.rest.api.entity.Comment;
import lombok.Data;

import java.util.Set;

@Data
public class PostResponseDTO {

    private Long id;

    private String title;

    private String description;

    private String content;

    private Set<CommentResponseDTO> comments;
}

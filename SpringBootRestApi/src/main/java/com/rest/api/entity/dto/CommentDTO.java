package com.rest.api.entity.dto;

import com.rest.api.entity.Post;
import lombok.Data;


@Data
public class CommentDTO {


    private String name;


    private String email;


    private String body;


  //  private Post post;
    private Post post; //todo POST ????

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
//todo

//    public Post getPost() {
//        return post;
//    }
//
//    public void setPost(Post post) {
//        this.post = post;
//    }
}

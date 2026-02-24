package com.example.vibeapp.post.mapper;

import com.example.vibeapp.post.Post;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PostMapper {
    List<Post> findAll();

    Post findById(Long no);

    void save(Post post);

    void update(Post post);

    void delete(Long no);

    void incrementViews(Long no);
}

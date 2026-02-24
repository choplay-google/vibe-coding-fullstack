package com.example.vibeapp.post;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PostRepository {
    List<Post> findAll();

    // Pagination support
    List<Post> findAllWithPaging(@Param("offset") int offset, @Param("size") int size);

    // Total count support
    int countAll();

    Post findById(Long no);

    void save(Post post);

    void update(Post post);

    void delete(Long no);

    void incrementViews(Long no);
}

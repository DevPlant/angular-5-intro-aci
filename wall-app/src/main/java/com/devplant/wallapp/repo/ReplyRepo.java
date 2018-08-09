package com.devplant.wallapp.repo;

import com.devplant.wallapp.model.Post;
import com.devplant.wallapp.model.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepo extends JpaRepository<Reply, Long> {

    Page<Reply> findAllByPost(Post post, Pageable pageable);

    void deleteByPost(Post post);
}

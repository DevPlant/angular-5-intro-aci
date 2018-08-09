package com.devplant.wallapp.repo;

import com.devplant.wallapp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Long>{


}

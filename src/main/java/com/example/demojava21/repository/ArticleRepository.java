package com.example.demojava21.repository;

import com.example.demojava21.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    // 这里可以定义特定的查询方法，例如：
    List<Article> findByTitleContaining(String title);
}

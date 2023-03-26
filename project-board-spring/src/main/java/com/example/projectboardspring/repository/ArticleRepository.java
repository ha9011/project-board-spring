package com.example.projectboardspring.repository;

import com.example.projectboardspring.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
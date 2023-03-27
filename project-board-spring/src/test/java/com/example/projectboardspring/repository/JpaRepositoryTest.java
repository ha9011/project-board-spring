package com.example.projectboardspring.repository;

import com.example.projectboardspring.config.JpaConfig;
import com.example.projectboardspring.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;

    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired  ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("insert test")
    @Test
    void givenTestData_whenSelecting_thenWorksFine(){
        // Given
        long prevCount = articleRepository.count();

        // When
        Article saveArticle = articleRepository.save(Article.of("new article", "new content", "new hashtag"));
        List<Article> articles = articleRepository.findAll();

        // Then
        assertThat(articleRepository.count())
                .isEqualTo(prevCount + 1);
    }

    @DisplayName("update test")
    @Test
    void givenTestData_whenUpdating_thenWorksFine(){
        // Given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updateHashTag = "#SpringBoot";
        article.setHashtag(updateHashTag);

        // When
        Article saveArticle = articleRepository.save(article);
        //Article saveArticle = articleRepository.saveAndFlush(article);

        // Then
        assertThat(saveArticle.getHashtag())
                .isEqualTo(updateHashTag);

        assertThat(saveArticle)
                .hasFieldOrPropertyWithValue("hashtag", updateHashTag);
    }


    @DisplayName("delete test")
    @Test
    void givenTestData_whenDeleting_thenWorksFine(){
        // Given
        Article article = articleRepository.findById(1L).orElseThrow();
        long prevArticleCount = articleRepository.count();
        long prevArticleCommentCount = articleCommentRepository.count();
        int deletedCommentsSize = article.getArticleComments().size();


        // When
        articleRepository.delete(article);

        // Then
        assertThat(articleRepository.count())
                .isEqualTo(prevArticleCount-1);

        assertThat(articleCommentRepository.count())
                .isEqualTo(prevArticleCommentCount-deletedCommentsSize);
    }
}
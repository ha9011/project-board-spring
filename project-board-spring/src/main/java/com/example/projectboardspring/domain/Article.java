package com.example.projectboardspring.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.core.annotation.Order;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String title; /* 제목 */

    @Setter
    @Column(nullable = false, length = 10000)
    private String content; /* 내용 */

    @Setter
    private String hashtag; /* 해시태그 */


    //중복을 허용하지 않겠다.
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    @ToString.Exclude
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();



    // id, 메타데이터는 자동으로 입력되기 때문에 클레스에 @setter를 따로 안함
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(nullable = false, length = 100)
    private String createdBy;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String modifiedBy;


    protected Article(){}

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // TODO 팩토리 함수
    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    // TODO Lombok의 EqualsAndHashCodes는 모든 필드를 같이 체크하기 때문에 리소스낭비
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

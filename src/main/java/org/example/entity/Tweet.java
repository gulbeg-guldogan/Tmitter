package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
//@Data
@Entity
@Table(name = "ttweet", schema = "tmitter")
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long tweet_id;

    @Column(nullable = false, length = 400)
    private String content;

    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Long getTweet_id() { return tweet_id; }
    public void setTweet_id(Long tweet_id) { this.tweet_id = tweet_id; }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweet other = (Tweet) o;
        return Objects.equals(tweet_id, other.tweet_id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(tweet_id);
    }
}

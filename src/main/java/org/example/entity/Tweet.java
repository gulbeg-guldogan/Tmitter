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
@Table(name = "tTweet", schema = "public")
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
    @JoinColumn(name = "id", nullable = false)
    private User user;

    @Override
    public boolean equals(Object obj){
        if(obj == this)
            return true;

        if(obj == null || obj.getClass() != getClass())
            return false;

        User user = (User) obj;
        return user.getTweets().equals(tweet_id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(tweet_id);
    }
}

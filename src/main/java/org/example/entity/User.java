package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
//@Data
@Entity
@Table(name="tuser", schema = "tmitter")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true, nullable = false, length = 70)
    private String username;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 70)
    private String password;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime create_time;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime last_entry;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tweet> tweets = new ArrayList<>();


    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Long getUserId() { return id; }
    public void setUserId(Long userId) { this.id = id; }

    public void addTweet(Tweet tweet){
        if (tweet == null) {
            throw new IllegalArgumentException("Tweet cannot be null");
        }

        if(tweet.getUser().equals(this) && !tweets.contains(tweet))
            tweets.add(tweet);
            tweet.setUser(this);
    }

    public void removeTweet(Tweet tweet){
        tweets.remove(tweet);
        tweet.setUser(null);
    }

    public List<Tweet> getTweets(){
        return Collections.unmodifiableList(this.tweets);
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this)
            return true;

        if(obj == null || obj.getClass() != getClass())
            return false;

        User user = (User) obj;
        return user.getUserId().equals(id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}

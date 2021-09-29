package entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String e_mail;
    private String f_name;
    private String l_name;

    @OneToMany(mappedBy = "user")
    private final List<Poll> polls = new ArrayList<>();
    public List<Poll> getPolls() { return polls; }
}

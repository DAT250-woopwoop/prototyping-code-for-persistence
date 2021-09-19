package entity;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
public class Polls {
    public Polls(){
        pastPolls = 0;
        currentPolls = 0;
        futurePolls = 0;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int pastPolls;
    private int currentPolls;
    private int futurePolls;

    @OneToMany(mappedBy = "polls")
    private List<Poll> pollsList;

    @OneToOne
    private User user;
}

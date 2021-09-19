package entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Poll {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pollDesc;
    private String pollName;
    private String startTime; //TODO: Change to given best time class
    private String endTime;
    private String timeLimit;
    private boolean privatePoll;
    private boolean closed;
    private int yesOption;
    private int noOption;

    @ManyToOne
    private Polls polls;

    public Poll() {
        pollDesc="";
        pollName ="";
        startTime="";
        endTime="";
        timeLimit="";
        privatePoll=false;
        closed=false;
        yesOption=0;
        noOption=0;
    }
}

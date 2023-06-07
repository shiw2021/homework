package hziee.spc.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "record")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "app")
    private Applicant applicant;


    @Column(name = "stage")
    private Integer stage;

    @Column(name = "passed")
    private boolean passed;

    @Column(name = "opinion")
    private String opinion;

    @OneToOne
    @JoinColumn(name = "signer")
    private User signer;



    // constructors, getters, setters, etc.
}

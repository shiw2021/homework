package hziee.spc.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "background_working")
public class BackgroundWorking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_applicant", nullable = true)
    private Applicant idApplicant;

    @JsonProperty("start")
    @Column(name = "date_start")
    private LocalDate dateStart;

    @JsonProperty("end")
    @Column(name = "date_end")
    private LocalDate dateEnd;

    @Column(name = "company")
    private String company;

    @Column(name = "position")
    private String position;

    @JsonProperty("leftreason")
    @Lob
    @Column(name = "resign_reasion")
    private String resignReasion;

}
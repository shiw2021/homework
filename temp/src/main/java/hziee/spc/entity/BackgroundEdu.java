package hziee.spc.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "background_edu")
public class BackgroundEdu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_applicant")
    private Applicant idApplicant;

    @JsonProperty("start")
    @Column(name = "date_start")
    private LocalDate dateStart;

    @JsonProperty("end")
    @Column(name = "date_end")
    private LocalDate dateEnd;

    @Column(name = "school")
    private String school;

    @Column(name = "major")
    private String major;

}
package hziee.spc.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "applicants_relationships")
public class ApplicantsRelationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;



    @ManyToOne
    @JoinColumn(name = "id_applicant", nullable = true)
    private Applicant applicant;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @JsonProperty("relation")
    @Column(name = "relationship")
    private String relationship;

    @Column(name = "company")
    private String company;

    @Column(name = "position")
    private String position;

}
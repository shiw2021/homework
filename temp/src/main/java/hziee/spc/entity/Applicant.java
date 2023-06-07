package hziee.spc.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "applicants")
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`index`", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;



    @Column(name = "gender", nullable = false)
    private Character gender;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "age")
    private Integer age;

    @Column(name = "ethnic_group", nullable = false, length = 20)
    private String ethnicGroup;

    @Column(name = "height")
    private Integer height;


    @Column(name = "hu_ji_province", length = 20)
    private String huJiProvince;

    @Column(name = "hu_ji_city", length = 20)
    private String huJiCity;

    @Column(name = "ju_zhu_province", length = 20)
    private String juZhuProvince;

    @Column(name = "ju_zhu_city")
    private String juZhuCity;

    @Column(name = "married")
    private Boolean married;

    @Column(name = "working_years")
    private Integer workingYears;

    @Column(name = "expected_salary")
    private Integer expectedSalary;

    @Column(name = "edu_bacground", length = 20)
    private String eduBacground;

    @Column(name = "major")
    private String major;

    @Column(name = "school")
    private String school;

    @Column(name = "status")
    private String status;

    @Column(name = "has_relationship")
    private Boolean hasRelationship;

    @Column(name = "relator")
    private String relator;

    @Column(name = "accept_overtime")
    private Boolean acceptOvertime;

    @Column(name = "accept_transfer")
    private Boolean acceptTransfer;

    @JsonProperty("Card")
    @Column(name = "IDcard")
    private String IDcard;

    @JsonProperty("Address")
    @Column(name = "IDcard_address")
    private String IDcard_address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "landline")
    private String landline;


    @Column(name = "portrait_url")
    private String portraitUrl;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "position_applyingfor")
    private String positionApplyingFor;

    @Column(name = "signature_url")
    private String signatureUrl;

    @Column(name = "dep_id")
    private  Integer departmentId;

    @JsonProperty("work")
    @OneToMany(mappedBy = "idApplicant", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<BackgroundWorking> backgroundWorkings = new LinkedHashSet<>();

    @JsonProperty("relation")
    @OneToMany(mappedBy = "applicant",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ApplicantsRelationship> applicantsRelationships;

    @JsonProperty("edu")
    @OneToMany(mappedBy = "idApplicant",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<BackgroundEdu> backgroundEdus = new LinkedHashSet<>();

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<Record> records;
    @Override
    public String toString() {
        return "Applicant{" +
                "gender=" + gender +
                ", birthday=" + birthday +
                ", married=" + married +
                ", hasRelationship=" + hasRelationship +
                ", relator='" + relator + '\'' +
                ", acceptOvertime=" + acceptOvertime +
                ", acceptTransfer=" + acceptTransfer +
                ", portraitUrl='" + portraitUrl + '\'' +
                ", date=" + date +
                ", signatureUrl='" + signatureUrl + '\'' +
                ", backgroundWorkings=" + backgroundWorkings +
                ", applicantsRelationship=" +
                ", backgroundEdus=" + backgroundEdus +
                '}';
    }
}
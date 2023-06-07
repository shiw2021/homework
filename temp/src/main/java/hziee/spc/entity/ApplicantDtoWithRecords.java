package hziee.spc.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the {@link Applicant} entity
 */
@Data
@Setter
@Getter

public class ApplicantDtoWithRecords implements Serializable {
    private Integer id;
    private String name;
    private String portraitUrl;
    private String status;
    private Character gender;
    private LocalDate birthday;
    private Integer age;
    private String phone;
    private String positionApplyingFor;
    private Integer departmentId;
    private List<RecordDto> records;

    public List<ApplicantDtoWithRecords> convert(Iterable<Applicant> applicants) {
        List<ApplicantDtoWithRecords> applicantDtoWithRecords = new ArrayList<>();
        applicants.iterator().forEachRemaining(applicant -> {
            ApplicantDtoWithRecords applicantDtoWithRecord = new ApplicantDtoWithRecords();
            applicantDtoWithRecord.setId(applicant.getId());
            applicantDtoWithRecord.setName(applicant.getName());
            applicantDtoWithRecord.setPortraitUrl(applicant.getPortraitUrl());
            applicantDtoWithRecord.setStatus(applicant.getStatus());
            applicantDtoWithRecord.setGender(applicant.getGender());
            applicantDtoWithRecord.setBirthday(applicant.getBirthday());
            applicantDtoWithRecord.setAge(applicant.getAge());
            applicantDtoWithRecord.setPhone(applicant.getPhone());
            applicantDtoWithRecord.setPositionApplyingFor(applicant.getPositionApplyingFor());
            applicantDtoWithRecord.setDepartmentId(applicant.getDepartmentId());
            List<RecordDto> recordDtos = new ArrayList<>();
            applicant.getRecords().iterator().forEachRemaining(record -> {
                RecordDto recordDto = new RecordDto();
                recordDto.setId(record.getId());
                recordDto.setStage(record.getStage());
                recordDto.setPassed(record.isPassed());
                recordDto.setOpinion(record.getOpinion());
                recordDto.setSigner(record.getSigner().getId());
                recordDtos.add(recordDto);
            });
            applicantDtoWithRecord.setRecords(recordDtos);
            applicantDtoWithRecords.add(applicantDtoWithRecord);
        });
        return applicantDtoWithRecords;
    }


    /**
     * A DTO for the {@link Record} entity
     */
    @Data
    public static class RecordDto implements Serializable {
        private Integer id;
        private Integer stage;
        private boolean passed;
        private String opinion;
        private Integer signer;
        private String signerName;

        public static List<RecordDto> convert(Iterable<Record> records) {
            List<RecordDto> recordDtos = new ArrayList<>();
            records.iterator().forEachRemaining(record -> {
                RecordDto recordDto = new RecordDto();
                recordDto.setId(record.getId());
                recordDto.setStage(record.getStage());
                recordDto.setPassed(record.isPassed());
                recordDto.setOpinion(record.getOpinion());
                recordDto.setSigner(record.getSigner().getId());
                recordDto.setSignerName(record.getSigner().getName());
                recordDtos.add(recordDto);
            });
            return recordDtos;
        }
    }
}
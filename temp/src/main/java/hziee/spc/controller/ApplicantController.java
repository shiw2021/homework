package hziee.spc.controller;

import hziee.spc.entity.Applicant;
import hziee.spc.entity.ApplicantDtoWithRecords;

import hziee.spc.entity.Record;
import hziee.spc.entity.User;
import hziee.spc.repository.ApplicantRepository;
import hziee.spc.repository.RecordRepository;
import hziee.spc.util.WordTableFiller;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ApplicantController {

    private final ApplicantRepository applicantRepository;
    private final RecordRepository recordRepository;

    public ApplicantController(ApplicantRepository applicantRepository, RecordRepository recordRepository) {
        this.applicantRepository = applicantRepository;
        this.recordRepository = recordRepository;
    }

    @PostMapping("/applicant")
    public ResponseEntity<Object> postApplicant(@RequestBody Applicant applicant) throws Exception {


//        applicant.setId(100);
        //设置record的stage为1
        Record officeRecord = new Record();
        officeRecord.setStage(1);
        officeRecord.setPassed(true);
        officeRecord.setApplicant(applicant);

        //赋值给applicant
        List<Record> records = new ArrayList<>();
        records.add(officeRecord);
        applicant.setRecords(records);


        applicant.getBackgroundEdus().iterator().forEachRemaining(backgroundEdu -> {
            backgroundEdu.setIdApplicant(applicant);
        });
        applicant.getBackgroundWorkings().iterator().forEachRemaining(backgroundWorking -> backgroundWorking.setIdApplicant(applicant));
        applicant.getApplicantsRelationships().iterator().forEachRemaining(applicantsRelationship -> applicantsRelationship.setApplicant(applicant));
        //check if the applicant is saved successfully
        Applicant savedApplicant = applicantRepository.save(applicant);
        if (savedApplicant.getId() == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }


    }

    //download docx file by id
    @GetMapping(value = "/applicant/download/{id}", produces = {"application/octet-stream"})
    public ResponseEntity<byte[]> getApplicantById(@PathVariable Integer id) throws IOException {
        Optional<Applicant> applicant = applicantRepository.findById(id);
        if (applicant.isPresent()) {
            //fill the docx file and get file , then response the file to front end
            WordTableFiller wordTableFiller = new WordTableFiller();
            //get path of the docx file
            String path = wordTableFiller.fill(applicant.get());
            //File file = ResourceUtils.getFile(path);
            //byte[] contents = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            byte[] contents = Files.readAllBytes(Path.of(path));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.attachment().filename("yourfile.docx").build());
            return new ResponseEntity<>(contents, headers, HttpStatus.OK);

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/applicant")
    public ResponseEntity<Object> getApplicant(@RequestParam(required = false) String name) {

        Iterable<Applicant> applicants = applicantRepository.findAll();
        applicants.iterator().forEachRemaining(applicant -> {
            applicant.setApplicantsRelationships(null);
            applicant.setBackgroundEdus(null);
            applicant.setBackgroundWorkings(null);
            applicant.setRecords(null);
        });

        return ResponseEntity.status(HttpStatus.OK).body(applicants);
    }

    @PostMapping("/applicant/filtered")
    public ResponseEntity<Object> getFilteredApp(@RequestBody User user) {
        int dId = user.getDepartmentId();
        Iterable<Applicant> applicants;
        if (dId == 2 || dId == 3 || dId == 7) {
            applicants = applicantRepository.findAll();
        } else {
            applicants = applicantRepository.findByDepartmentId(dId);
        }
        List<ApplicantDtoWithRecords> applicantDtoWithRecords = new ArrayList<>();
        applicants.iterator().forEachRemaining(applicant -> {
            applicant.setApplicantsRelationships(null);
            applicant.setBackgroundEdus(null);
            applicant.setBackgroundWorkings(null);
        });
        applicantDtoWithRecords = new ApplicantDtoWithRecords().convert(applicants);
        return ResponseEntity.status(HttpStatus.OK).body(applicantDtoWithRecords);
    }

    @PutMapping("/applicant/{id}")
    public ResponseEntity<Object> putUser(@PathVariable Integer id, @RequestBody Applicant applicant) {
        Optional<Applicant> applicantOptional = applicantRepository.findById(id);
        if (applicantOptional.isPresent()) {

            applicantRepository.save(applicant);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no such applicant in database");
        }
    }
}

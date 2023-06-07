package hziee.spc.controller;

import hziee.spc.entity.*;
import hziee.spc.entity.DTO.RecordAndUserDto;
import hziee.spc.entity.Record;
import hziee.spc.repository.ApplicantRepository;
import hziee.spc.repository.DepartmentRepository;
import hziee.spc.repository.RecordRepository;
import hziee.spc.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RecordController {

    private final ApplicantRepository applicantRepository;
    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    public RecordController(ApplicantRepository applicantRepository, RecordRepository recordRepository, UserRepository userRepository,
                            DepartmentRepository departmentRepository) {
        this.applicantRepository = applicantRepository;
        this.recordRepository = recordRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }


    //解析 record对象 和 user对象
    @PostMapping("/record/{appId}")
    public ResponseEntity<Object> postRecord(
            @PathVariable("appId") Integer appId,
            @RequestBody RecordAndUserDto recordAndUserDto
    ) {
        Record record = recordAndUserDto.getRecord();
        User user = recordAndUserDto.getUser();

        Optional<Applicant> optionalApplicant = applicantRepository.findById(appId);
        Optional<User> optionalUser = userRepository.findById(user.getId());

        if (optionalApplicant.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Applicant applicant = optionalApplicant.get();
        User signer = optionalUser.get();
        if (!signer.getPwd().equals(user.getPwd())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("密码错误");
        }
        //判断 用户是否符合 为applicant添加 record的条件
        //判断依据：办公部门的用户，可以对阶段为1 的applicant添加record
        //用人部门的用户，只能对申请部门为相同部门的applicant添加record
        //总经理只能对阶段为3 的applicant添加record
        //hr不能操作
        boolean flag = true;
        int appDepId = applicant.getDepartmentId();
        int usrDepId = user.getDepartmentId();
        Department appDep = departmentRepository.findOneById(appDepId);
        Department usrDep = departmentRepository.findOneById(usrDepId);
        if (usrDep.getName().equals("人事部")) {
            flag = false;
        } else if (usrDep.getName().equals("办公部")) {
            if (record.getStage() != 2) {
                flag = false;
            }
        } else if (usrDep.getName().equals("总经理")) {
            if (record.getStage() != 4) {
                flag = false;
            }
        } else if (appDepId != usrDepId) {
            flag = false;
        }
        if (flag) {
            record.setSigner(signer);
            record.setApplicant(applicant);
            Record savedRecord = recordRepository.save(record);
            return ResponseEntity.created(null).build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("您没有权限操作该申请");
        }
    }

    //get records
    @GetMapping("/record/{appId}")
    public ResponseEntity<Object> getRecords(
            @PathVariable("appId") Integer appId
    ) {
        Iterable<Record> records = recordRepository.findAllByApplicantId(appId);
        List<ApplicantDtoWithRecords.RecordDto> recordDtos = ApplicantDtoWithRecords.RecordDto.convert(records);

        return ResponseEntity.ok().body(recordDtos);
    }
}

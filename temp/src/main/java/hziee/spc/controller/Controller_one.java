package hziee.spc.controller;

import hziee.spc.entity.Admin;
import hziee.spc.entity.Department;
import hziee.spc.repository.AdminRepository;
import hziee.spc.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class Controller_one {
    final
    AdminRepository adminRepository;
    final
    DepartmentRepository departmentRepository;

    public Controller_one(AdminRepository adminRepository, DepartmentRepository departmentRepository) {
        this.adminRepository = adminRepository;
        this.departmentRepository = departmentRepository;
    }

    @PostMapping("/sendInfo")
    public void sendInfo() {

    }

    @PostMapping("/adminLogin")
    public ResponseEntity<Map<String, Object>> adminLogin(@Validated @RequestBody Admin admin) {
        Map<String, Object> result = new HashMap<>();
        try {
            Admin admin1 = adminRepository.findByEmailAndPwd(admin.getEmail(), admin.getPwd());
            if (admin1 == null) {
                result.put("code", 401);
                result.put("msg", "Unauthorized");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
            } else {
                result.put("code", 200);
                result.put("msg", "Success");
                result.put("admin", admin1);
                return ResponseEntity.ok(result);
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "Internal Server Error");
            result.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @GetMapping("/getDepartments")
    public ResponseEntity<Map<String, Object>> getDepartments(@RequestParam(required = false) String name) {

        Map<String, Object> result = new HashMap<>();
        Iterable<Department> departmentIterable;
        if (!StringUtils.hasText(name))
            departmentIterable = departmentRepository.findAll();
        else {
            departmentIterable = departmentRepository.findAllByName(name);
        }
        departmentIterable.forEach(department -> {
            System.out.println(department.toString());

        });
        result.put("code", 200);
        result.put("msg", "Success");
        result.put("Departments", departmentIterable);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/addDepartment/{name}")
    public ResponseEntity<String> addDepartment(@PathVariable String name) {
        Department department = new Department();
        department.setName(name);
        departmentRepository.save(department);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/deleteDepartment/{id}")
    public ResponseEntity<Map<String, Object>> deleteDepartment(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            departmentRepository.deleteById(id);
            result.put("code", 200);
            result.put("msg", "Success");
            return ResponseEntity.ok(result);
        } catch (EmptyResultDataAccessException e) {
            result.put("code", 404);
            result.put("msg", "Department not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("msg", "Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<String> updateDepartmentName(@PathVariable Long id, @RequestBody Department departmentDto) {
        Optional<Department> departmentOptional = departmentRepository.findById(Math.toIntExact(id));
        if (departmentOptional.isPresent()) {
            Department department = departmentOptional.get();
            department.setName(departmentDto.getName());
            departmentRepository.save(department);
        } else {
            departmentRepository.save(departmentDto);
        }
        return ResponseEntity.ok("Success");
    }
}

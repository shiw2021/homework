package hziee.spc.repository;

import hziee.spc.entity.User;
import hziee.spc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> searchUsers(String email, Integer departmentId) {
        Iterable<User> userIterable;
        if (email != null && departmentId != null) {
            userIterable = userRepository.findByEmailAndDepartmentId(email, departmentId);
        } else if (email != null) {
            userIterable = userRepository.findByEmailContaining(email);
        } else if (departmentId != null) {
            userIterable = userRepository.findByDepartmentId(departmentId);
        } else {
            userIterable = userRepository.findAll();
        }
        return userIterable;
    }
}

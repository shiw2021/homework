package hziee.spc.controller;

import hziee.spc.entity.User;
import hziee.spc.repository.UserRepository;
import hziee.spc.repository.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @PostMapping("/userLogin")
    public ResponseEntity<Object> userLogin(@RequestBody User u){
        User user=userRepository.findByEmailAndPwd(u.getEmail(),u.getPwd());
        if (user!=null){
            user.setPwd(null);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @PostMapping("/createUser")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        if ((userRepository.findOneByEmail(user.getEmail())) != null) {
            System.out.println((userRepository.findOneByEmail(user.getEmail())).toString());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email address is already in use.");
        }
        userRepository.save(user);
        return ResponseEntity.ok("User created successfully");
    }

    @GetMapping("/getUsers")
    public ResponseEntity<Object> getUsers(@RequestParam(required = false) String email,
                                           @RequestParam(required = false) Integer departmentId) {
        Iterable<User> userIterable = userService.searchUsers(email, departmentId);
        System.out.println(userIterable.toString());

        return ResponseEntity.status(HttpStatus.OK).body(userIterable);
    }

    @PutMapping("/putUser/{id}")
    public ResponseEntity<Object> putUser(@PathVariable Integer id, @RequestBody User u) {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            User newUser = userData.get();
            newUser.setEmail(u.getEmail());
            newUser.setName(u.getName());
            newUser.setDepartmentId(u.getDepartmentId());
            userRepository.save(newUser);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no such user in database");
        }
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        Optional<User> userData = userRepository.findById(id);
        if (userData.isPresent()) {
            userRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/resetPwd/{id}")
    public ResponseEntity<Object> resetPwd(@PathVariable Integer id) {

        User userData = userRepository.findOneById(id);

        if (userData != null) {
            userData.setPwd("111111");
            userRepository.save(userData);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

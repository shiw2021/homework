package hziee.spc.repository;

import hziee.spc.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {


    Iterable<User> findByEmailAndDepartmentId(String email, Integer departmentId);

    Object findOneByEmail(String email);

    Iterable<User> findByEmail(String email);

    Iterable<User> findByDepartmentId(Integer departmentId);

    User findOneById(Integer id);

    @Query("select u from User u where u.email = ?1")
    Iterable<User> findByEmailLike(String email);

    Iterable<User> findByEmailStartingWith(String email);

    Iterable<User> findByEmailContaining(String email);

    User findByEmailAndPwd(String email, String pwd);
}

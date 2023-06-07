package hziee.spc.repository;

import hziee.spc.entity.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, Integer> {
    Admin findByEmailAndPwd(String email, String pwd);

}
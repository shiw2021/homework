package hziee.spc.repository;

import hziee.spc.entity.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {
    Iterable<Department> findAllByName(String name);

    Department findOneById(int usrDepId);
}
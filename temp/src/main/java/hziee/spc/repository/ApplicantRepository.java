package hziee.spc.repository;

import hziee.spc.entity.Applicant;
import org.springframework.data.repository.CrudRepository;

public interface ApplicantRepository extends CrudRepository<Applicant, Integer> {

    Iterable<Applicant> findByDepartmentId(int dId);
}

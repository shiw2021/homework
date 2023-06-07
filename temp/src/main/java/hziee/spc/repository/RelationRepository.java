package hziee.spc.repository;

import hziee.spc.entity.ApplicantsRelationship;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface     RelationRepository extends CrudRepository<ApplicantsRelationship, Integer> {

}

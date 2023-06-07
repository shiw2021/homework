package hziee.spc.repository;

import hziee.spc.entity.Record;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RecordRepository extends CrudRepository<Record, Integer> {
    Iterable<Record> findAllByApplicantId(Integer appId);
}

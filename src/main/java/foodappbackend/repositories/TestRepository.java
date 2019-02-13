package foodappbackend.repositories;

import foodappbackend.model.TestEntity;
import org.springframework.data.repository.CrudRepository;

public interface TestRepository extends CrudRepository<TestEntity, Long> {
    Iterable<TestEntity> findAll();
}

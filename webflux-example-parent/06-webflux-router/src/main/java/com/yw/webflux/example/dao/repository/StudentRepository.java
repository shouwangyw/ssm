package com.yw.webflux.example.dao.repository;

import com.yw.webflux.example.dao.po.Student;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author yangwei
 */
@Repository
public interface StudentRepository extends ReactiveMongoRepository<Student, String> {
}

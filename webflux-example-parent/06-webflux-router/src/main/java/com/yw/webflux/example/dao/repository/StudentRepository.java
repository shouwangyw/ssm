package com.yw.webflux.example.dao.repository;

import com.yw.webflux.example.dao.po.Student;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @author yangwei
 */
@Repository
public interface StudentRepository extends ReactiveMongoRepository<Student, String> {
    /**
     * 根据年龄查询
     *
     * @param below 年龄下限（不包含）
     * @param top   年龄上限（不包含）
     * @return
     */
    Flux<Student> findByAgeBetween(int below, int top);

    /**
     * 使用Mongo原生查询
     */
    @Query("{'age':{'$gt':?0, '$lte':?1}}")
    Flux<Student> findByAge(int below, int top);
}

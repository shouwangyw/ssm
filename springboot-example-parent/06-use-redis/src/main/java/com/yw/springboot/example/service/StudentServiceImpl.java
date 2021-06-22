package com.yw.springboot.example.service;

import com.yw.springboot.example.dao.mapper.StudentMapper;
import com.yw.springboot.example.dao.po.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @author yangwei
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @CacheEvict(value = "realTimeCache", allEntries = true)
    // 采用spring默认的事务提交方式: 发生运行时异常回滚
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addStudent(Student student) {
        int result = studentMapper.insertStudent(student);
        int i = 3 / (int) (Math.random() * 10);
        return result >= 1;
    }

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Cacheable(value = "realTimeCache", key = "'student_'+#id")
    @Override
    public Student findStudentById(int id) {
        System.out.println("从DB中查询student");
        return studentMapper.selectStudentById(id);
    }

    /**
     * 使用双重检测锁解决热点缓存问题
     */
    @Override
    public Integer findStudentsCount() {
        // 获取Redis操作对象
        BoundValueOperations<Object, Object> ops = redisTemplate.boundValueOps("count");
        // 从缓存中读取数据
        Object count = ops.get();
        if (count == null) {
            synchronized (this) {
                count = ops.get();
                if (count == null) {
                    // 从DB中查询数据
                    count = studentMapper.selectStudentsCount();
                    // 将数据写入到缓存，并设置到期时限
                    ops.set(count, 10, TimeUnit.SECONDS);
                }
            }
        }
        return (Integer) count;
    }
}
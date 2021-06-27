package com.yw.webflux.example.controller;

import com.yw.webflux.example.dao.po.Student;
import com.yw.webflux.example.dao.repository.StudentRepository;
import com.yw.webflux.example.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author yangwei
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentRepository repository;

    // 一次性返回数据
    @GetMapping("/all")
    public Flux<Student> getAll() {
        return repository.findAll();
    }

    // 以SSE形式实时性返回数据
    @GetMapping(value = "/sse/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Student> getAllSse() {
        return repository.findAll();
    }

    /**
     * 添加对象
     */
    @PostMapping("/save")
    public Mono<Student> saveStudent(@Valid @RequestBody Student student) {
        // 进行姓名的合法性
        ValidateUtil.validName(student.getName());

        return repository.save(student);
    }

    /**
     * 无状态删除
     */
    @DeleteMapping("/delcomm/{id}")
    public Mono<Void> deleteStudent(@PathVariable("id") String id) {
        return repository.deleteById(id);
    }

    /**
     * 有状态删除
     * 需求：若删除的对象存在，且删除成功，则返回响应码200，否则返回响应码404
     * <p>
     * Mono<ResponseEntity<Void>>表示方法返回值为Mono序列
     * 其包含的元素为ResponseEntity对象，该对象中仅为包含响应状态码
     * <p>
     * map()与flatMap()均可做映射，但这两个方法与Stream编程中的两个同名方法没有任何关系
     * map()：同步方法
     * flatMap()：异步方法
     * 一般选择的标准是：若映射的内容中包含有耗时方法，则选择flatMap()，否则选择map()
     */
    @DeleteMapping("/delstat/{id}")
    public Mono<ResponseEntity<Void>> deleteStatStudent(@PathVariable("id") String id) {
        return repository.findById(id)
                .flatMap(stu -> repository.delete(stu)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 修改操作
     * 需求：若修改成功，则返回修改后的对象数据及200；若指定的id对象不存在，则返回404
     */
    @PutMapping("/update/{id}")
    public Mono<ResponseEntity<Student>> updateStudent(@PathVariable("id") String id,
                                                       @Valid @RequestBody Student student) {
        // 验证姓名和合法性
        ValidateUtil.validName(student.getName());

        return repository.findById(id)
                .flatMap(stu -> {
                    stu.setName(student.getName());
                    stu.setAge(student.getAge());
                    return repository.save(stu);
                })
                .map(stu -> new ResponseEntity<>(stu, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 根据id查询
     */
    @GetMapping("/find/{id}")
    public Mono<ResponseEntity<Student>> findStudentById(@PathVariable("id") String id) {
        return repository.findById(id)
                .map(stu -> new ResponseEntity<>(stu, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 根据年龄查询（普通返回）
     */
    @GetMapping("/age/{below}/{up}")
    public Flux<Student> findByAgeHandle(@PathVariable("below") int below,
                                         @PathVariable("up") int up) {
        return repository.findByAgeBetween(below, up);
    }

    @GetMapping(value = "/sse/age/{below}/{up}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Student> findByAgeSSEHandle(@PathVariable("below") int below,
                                            @PathVariable("up") int up) {
        return repository.findByAgeBetween(below, up);
    }

    /**
     * 根据年龄查询（普通返回）
     */
    @GetMapping("/find/age/{below}/{up}")
    public Flux<Student> findByAgeHandle2(@PathVariable("below") int below, @PathVariable("up") int up) {
        return repository.findByAge(below, up);
    }

    @GetMapping(value = "/sse/find/age/{below}/{up}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Student> findByAgeSSEHandle2(@PathVariable("below") int below, @PathVariable("up") int up) {
        return repository.findByAge(below, up);
    }
}

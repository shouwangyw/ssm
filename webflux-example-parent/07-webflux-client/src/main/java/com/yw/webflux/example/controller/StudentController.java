package com.yw.webflux.example.controller;

import com.yw.webflux.example.model.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 使用普通方式定义客户端处理器
 *
 * @author yangwei
 */
@RestController
public class StudentController {
    /**
     * 创建WebClient客户端，其参数baseUrl用于与下面处理器方法中的uri进行拼接
     * 向服务端提交请求
     */
    private WebClient client = WebClient.create("http://localhost:8080/student");

    @PostMapping("/save")
    public String saveStudentHandle(@RequestBody Student student) {
        Mono<Student> studentMono = client.post()
                .uri("/save")
                .body(Mono.just(student), Student.class)
                .retrieve() // 提交请求
                .bodyToMono(Student.class); // 接收服务器的响应
        // 输出每个Mono中的元素
        studentMono.subscribe(System.out::println);
        return "插入完毕";
    }

    @DeleteMapping("/del/{id}")
    public String deleteStudentHandle(@PathVariable("id") String id) {
        Mono<Void> voidMono = client.delete()
                .uri("/delstat/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
//        voidMono.subscribe();
        return "删除完毕";
    }

    @PutMapping("/update/{id}")
    public String updateStudentHandle(@PathVariable("id") String id, @RequestBody Student student) {
        Mono<ResponseEntity> mono = client.put()
                .uri("/update/{id}", id)
                .body(Mono.just(student), Student.class)
                .retrieve()
                .bodyToMono(ResponseEntity.class);
//        mono.subscribe();
        return "修改完毕";
    }

    @GetMapping("/list")
    public Flux<Student> listAllHandle() {
        Flux<Student> studentFlux = client.get()
                .uri("/all")
                .retrieve()
                .bodyToFlux(Student.class);
        studentFlux.subscribe(System.out::println);
        return studentFlux;
    }

    @GetMapping("/get/{id}")
    public Mono<Student> getStudentHandle(@PathVariable("id") String id) {
        Mono<Student> studentMono = client.get()
                .uri("/find/{id}", id)
                .retrieve()
                .bodyToMono(Student.class);
        studentMono.subscribe(System.out::println);
        return studentMono;
    }


    @PutMapping("/modify/{id}")
    public Flux<Student> modifyHandler(@PathVariable("id") String id, @RequestBody Student student) {
        Flux<Student> studentFlux = client.put()
                .uri("/update/{id}", id)
                .body(Mono.just(student), Student.class)
                .retrieve()
                .bodyToFlux(Student.class);
        studentFlux.subscribe(System.out::println);
        return studentFlux;
    }
}
package com.yw.webflux.example.handler;

import com.yw.webflux.example.model.Student;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author yangwei
 */
@Component
public class StudentHandler {
    /**
     * 创建WebClient客户端，其参数baseUrl用于与下面处理器方法中的uri进行拼接
     * 向服务端提交请求
     */
    private WebClient client = WebClient.create("http://localhost:8080/student");

    @PostMapping("/insert")
    public String insertHandler(@RequestBody Student student) {
        Flux<Student> studentFlux = client.post().uri("/save")
                .body(Mono.just(student), Student.class)
                .retrieve()
                .bodyToFlux(Student.class);
        studentFlux.subscribe(System.out::println);
        return "插入完毕";
    }

    @PutMapping("/modify/{id}")
    public String modifyHandler(@PathVariable("id") String id, @RequestBody Student student) {
        Flux<Student> studentFlux = client.put().uri("/update/{id}", id)
                .body(Mono.just(student), Student.class)
                .retrieve()
                .bodyToFlux(Student.class);
        studentFlux.subscribe(System.out::println);
        return "修改完毕";
    }

    @GetMapping("/get/{id}")
    public Mono<Student> getHandler(@PathVariable("id") String id) {
        Mono<Student> studentMono = client.get().uri("/find/{id}", id)
                .retrieve()
                .bodyToMono(Student.class);
        studentMono.subscribe();
        return studentMono;
    }
}

package com.yw.webflux.example.handler;

import com.yw.webflux.example.dao.po.Student;
import com.yw.webflux.example.dao.repository.StudentRepository;
import com.yw.webflux.example.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author yangwei
 */
@Component
public class StudentHandler {
    @Autowired
    private StudentRepository repository;

    // 查询所有
    public Mono<ServerResponse> findAllHandler(ServerRequest request) {
        return ServerResponse
                // 指定响应码200
                .ok()
                // 指定请求体中的内容类型为UTF8编码的JSON数据
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                // 构建响应体
                .body(repository.findAll(), Student.class);
    }

    // 添加数据
    public Mono<ServerResponse> saveHandler(ServerRequest request) {
        Mono<Student> studentMono = request.bodyToMono(Student.class);
        // 验证姓名
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(repository.saveAll(studentMono), Student.class);
    }
    // 添加数据(带校验)
    public Mono<ServerResponse> saveValidHandler(ServerRequest request) {
        Mono<Student> studentMono = request.bodyToMono(Student.class);
        return studentMono.flatMap(stu -> {
            // 验证姓名
            ValidateUtil.validName(stu.getName());
            return ServerResponse
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(repository.saveAll(studentMono), Student.class);
        });
    }

    // 有状态删除
    public Mono<ServerResponse> deleteHandler(ServerRequest request) {
        String id = request.pathVariable("id");
        return repository.findById(id)
                .flatMap(stu -> repository.delete(stu).then(ServerResponse.ok().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    // 修改数据
    public Mono<ServerResponse> updateHandler(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Student> studentMono = request.bodyToMono(Student.class);

        return studentMono.flatMap(stu -> {

            stu.setId(id);
            return ServerResponse
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(repository.save(stu), Student.class);
        });
    }
    // 修改数据(带校验)
    public Mono<ServerResponse> updateValidHandler(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Student> studentMono = request.bodyToMono(Student.class);

        return studentMono.flatMap(stu -> {
            // 验证姓名
            ValidateUtil.validName(stu.getName());
            stu.setId(id);
            return ServerResponse
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .body(repository.save(stu), Student.class);
        });
    }
}

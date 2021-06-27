package com.yw.webflux.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yangwei
 */
@Slf4j
@RestController
@RequestMapping("/some")
public class SomeController {

    @GetMapping("/common")
    public String commonHandle() {
        return "common handler";
    }

    /**
     * Mono: 表示包含0或1个元素的异步序列
     */
    @GetMapping("/mono")
    public Mono<String> monoHandle() {
        // 静态方法just()可用于指定该异步序列中所包含的元素
        return Mono.just("mono handler");
    }

    /**
     * Flux: 表示包含0个或多个元素的异步序列
     */
    @GetMapping("/flux")
    public Flux<String> fluxHandle() {
        return Flux.just("beijing", "shanghai", "guangzhou", "shenzhen");
    }

    /**
     * 数组转为Flux
     */
    @GetMapping("/array")
    public Flux<String> arrayHandle(@RequestParam String[] interests) {
        return Flux.fromArray(interests);
    }

    /**
     * 集合转为Flux
     */
    @GetMapping("/list")
    public Flux<String> listHandle(@RequestParam List<String> interests) {
        return Flux.fromStream(interests.stream());
    }

}

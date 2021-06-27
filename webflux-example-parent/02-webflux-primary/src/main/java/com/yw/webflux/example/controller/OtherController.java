package com.yw.webflux.example.controller;

import com.yw.webflux.example.util.Timer;
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
@RequestMapping("/other")
public class OtherController {

    @GetMapping("/common")
    public String commonHandle() {
        Timer timer = new Timer();
        String msg = doSome("common msg");
        log.info("耗时 {} ms", timer.elapsedTime());
        return msg;
    }

    /**
     * Mono: 表示包含0或1个元素的异步序列
     */
    @GetMapping("/mono")
    public Mono<String> monoHandle() {
        Timer timer = new Timer();
        Mono<String> monoMsg = Mono.fromSupplier(() -> doSome("mono msg"));
        log.info("耗时 {} ms", timer.elapsedTime());
        return monoMsg;
    }

    @GetMapping("/flux")
    public Flux<String> fluxHandle(@RequestParam List<String> interests) {
        Timer timer = new Timer();
        Flux<String> flux = Flux.fromStream(interests.stream().map(s -> doSome("elem-" + s + " ")));
        log.info("耗时 {} ms", timer.elapsedTime());
        return flux;
    }

    // SSE，Server Sent Event
    @GetMapping(value = "/sse", produces = "text/event-stream")
    public Flux<String> sseHandle() {
        return Flux.just("reading", "swimming", "fitness");
    }

    @GetMapping(value = "/sse/flux", produces = "text/event-stream")
    public Flux<String> sseFluxHandle(@RequestParam List<String> interests) {
        Timer timer = new Timer();
        Flux<String> flux = Flux.fromStream(interests.stream().map(s -> doSome("elem-" + s + " ")));
        log.info("耗时 {} ms", timer.elapsedTime());
        return flux;
    }

    /**
     * 模拟耗时操作
     */
    private String doSome(String msg) {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception ignore) {}
        return msg;
    }
}

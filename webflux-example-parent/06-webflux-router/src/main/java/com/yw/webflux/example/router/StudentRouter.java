package com.yw.webflux.example.router;

import com.yw.webflux.example.handler.StudentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author yangwei
 */
@Configuration
public class StudentRouter {
    @Bean
    RouterFunction<ServerResponse> customRouter(StudentHandler handler) {
        return RouterFunctions.nest(
                RequestPredicates.path("/student"),
                RouterFunctions.route(
                        RequestPredicates.GET("/all"),
                        handler::findAllHandler
                ).andRoute(
                        RequestPredicates.POST("/save").and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)),
//                      handler::saveHandler
                        handler::saveValidHandler
                ).andRoute(
                        RequestPredicates.DELETE("/del/{id}"),
                        handler::deleteHandler
                ).andRoute(
                        RequestPredicates.PUT("/update/${id}"),
//                    handler::updateHandler
                        handler::updateValidHandler
                )
        );
    }
}
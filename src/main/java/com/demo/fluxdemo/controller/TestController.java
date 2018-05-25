package com.demo.fluxdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RestController
@Slf4j
public class TestController {

    //原来的方式
    @GetMapping("/1")
    public String getMessage1(){
        log.info("get1 start");
        String s = createMsg();
        log.info("get1 end");
        return s;
    }

    //Mono：0到1一个元素
    @GetMapping("/2")
    public Mono<String> getMessage2(){
        log.info("get2 start");
        Mono<String> s = Mono.fromSupplier(() -> createMsg());
        log.info("get2 end");
        return s;
    }


    //Flux：0-N个元素 ;
    @GetMapping(value = "/3",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getMessage3(){
        return Flux.fromStream(IntStream.range(1,5).mapToObj(i ->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "flux" + i;
        }));
    }


    private String createMsg() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "some string!";
    }
}

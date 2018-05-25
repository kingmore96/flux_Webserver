package com.demo.fluxdemo;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.util.concurrent.TimeUnit;

public class FluxDemo {

    public static void main(String[] args){
        String[] strs = {"1","2","3"};

        //订阅者
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {

            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                //保存订阅关系，用于给发布者响应
                this.subscription = subscription;

                //请求一个数据
                subscription.request(1);
            }

            @Override
            public void onNext(Integer integer) {
                //接收到数据
                System.out.println("接收到数据" + integer);

                //模拟数据处理过程
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //请求下一个
                subscription.request(1);

                //如果请求完毕就关闭
                //subscription.cancel();

            }

            @Override
            public void onError(Throwable throwable) {
                //打印堆栈
                throwable.printStackTrace();

                //告诉发布者别发消息了
                subscription.cancel();
            }

            @Override
            public void onComplete() {
                //发布者关闭了
                System.out.println("处理完了！");
            }
        };

        //jdk8 的Stream + subscribe reactive Stream
        Flux.fromArray(strs).map(s -> Integer.parseInt(s))
                .subscribe(subscriber);
    }


}

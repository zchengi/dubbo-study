package com.cheng.dubbo;

import com.alibaba.dubbo.rpc.RpcContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author cheng
 *         2018/12/31 23:39
 */
public class ConsumerClient {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-dubbo-consumer.xml");

        context.start();

        while (true) {

            Scanner sc = new Scanner(System.in);
            String message = sc.next();

            // 获取接口
            ServiceAPI serviceApi = (ServiceAPI) context.getBean("consumerService");

            // 测试 负载均衡
            for (int i = 0; i < 2; i++) {
                System.out.println(serviceApi.sendMessage(message+"-" + i));
            }

            // 测试 异步调用
//            long beginTime = System.currentTimeMillis();

//            String send = serviceApi.sendMessage(message);
//            Future<String> sendFuture = RpcContext.getContext().getFuture();
//            long sendEndTime = System.currentTimeMillis();

//            String send2 = serviceApi.sendMessage2(message);
//            Future<String> sendFuture2 = RpcContext.getContext().getFuture();
//            long sendEndTime2 = System.currentTimeMillis();

//            System.out.println(send + ", " + send2
//            System.out.println(sendFuture.get() + ", " + sendFuture2.get()
//                    + ", send 执行时间: " + (sendEndTime - beginTime)
//                    + ", send2 执行时间: " + (sendEndTime2 - beginTime));
        }
    }
}

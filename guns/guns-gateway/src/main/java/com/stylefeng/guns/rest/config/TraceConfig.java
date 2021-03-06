package com.stylefeng.guns.rest.config;

import brave.spring.beans.TracingFactoryBean;
import com.alibaba.dubbo.rpc.protocol.dubbo.filter.TraceFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.okhttp3.OkHttpSender;

/**
 * Trace
 *
 * @author cheng
 *         2019/1/18 23:21
 */
@Configuration
public class TraceConfig {

    @Bean
    public TracingFactoryBean getTracingBean() {

        System.out.println(123123123);
        TracingFactoryBean tracingFactoryBean = new TracingFactoryBean();
        tracingFactoryBean.setLocalServiceName("gateway");

        tracingFactoryBean.setSpanReporter(AsyncReporter.create(OkHttpSender.create("http://127.0.0.1:9411/api/v2/spans")));

        return tracingFactoryBean;
    }
}

package com.cheng.dubbo.filter;

import com.alibaba.dubbo.rpc.*;

/**
 * @author cheng
 *         2019/1/18 21:51
 */
public class MyFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        System.out.println("服务调用之前 MethodName: "+invocation.getMethodName());
        // 调用后续服务
        Result invoke = invoker.invoke(invocation);
        System.out.println("服务调用之后");

        return invoke;
    }
}

package com.stylefeng.guns;

import com.stylefeng.guns.rest.AlipayApplication;
import com.stylefeng.guns.rest.common.util.FTPUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AlipayApplication.class)
public class AlipayApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private FTPUtil ftpUtil;

    @Test
    public void getPath() {
        System.out.println(ftpUtil.getFileStrByAddress("seats/cgs.json"));
    }

    @Test
    public void upload() {
        boolean success = ftpUtil.uploadFile("cgs.json", new File("D:/Appserv/ftp/seats/cgs.json"));

        System.out.println("上传是否成功: " + success);
    }
}

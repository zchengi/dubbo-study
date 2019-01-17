package com.stylefeng.guns.rest.common.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.*;

/**
 * @author cheng
 *         2019/1/15 17:21
 */
@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "ftp")
public class FTPUtil {

    private String hostname;
    private Integer port;
    private String username;
    private String password;
    private String uploadPath;

    private FTPClient ftpClient;

    /**
     * 文件上传
     */
    public boolean uploadFile(String filmName, File file) {

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);

            // FTP 初始化
            initFTPClient();

            // 设置 FTP 关键参数
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();

            // 修改 ftpClient 的工作空间
            ftpClient.changeWorkingDirectory(this.getUploadPath());

            // 上传文件
            ftpClient.storeFile(filmName, fileInputStream);

            return true;
        } catch (Exception e) {
            log.error("文件上传失败");
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                ftpClient.logout();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * 根据传入的路径，转换该文件为字符串返回
     *
     * @param fileAddress
     * @return
     */
    public String getFileStrByAddress(String fileAddress) {

        BufferedReader bufferedReader = null;
        try {
            initFTPClient();
            bufferedReader = new BufferedReader(
                    new InputStreamReader(ftpClient.retrieveFileStream(fileAddress)));

            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                String lineStr = bufferedReader.readLine();
                if (lineStr == null) {
                    break;
                }

                stringBuilder.append(lineStr);
            }
            ftpClient.logout();
            return stringBuilder.toString();

        } catch (IOException e) {
            log.error("获取文件信息失败", e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private void initFTPClient() {
        try {
            ftpClient = new FTPClient();
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.connect(hostname, port);
            ftpClient.login(username, password);
        } catch (Exception e) {
            log.error("FTP 初始化失败", e);
        }
    }

    public static void main(String[] args) {

        FTPUtil ftpUtil = new FTPUtil();
        System.out.println(ftpUtil.getFileStrByAddress("seats/cgs.json"));
    }
}

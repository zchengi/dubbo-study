package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.api.user.vo.UserInfoModel;
import com.stylefeng.guns.api.user.vo.UserModel;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户模块
 *
 * @author cheng
 *         2019/1/6 14:57
 */
@RestController
@RequestMapping("/user")
public class UserController {

    // todo 暂时设置为 false，项目启动的时候不检测是否有 Dubbo 提供者
    @Reference(interfaceClass = UserAPI.class,check = false)
    private UserAPI userAPI;

    @PostMapping("/register")
    public ResponseVO register(UserModel userModel) {

        if (StringUtils.isBlank(userModel.getUsername())) {
            return ResponseVO.serviceFail("用户名不能为空");
        }

        if (StringUtils.isBlank(userModel.getPassword())) {
            return ResponseVO.serviceFail("密码不能为空");
        }

        if (userAPI.register(userModel)) {
            return ResponseVO.success("用户注册成功");
        } else {
            return ResponseVO.success("用户注册失败");
        }
    }

    @PostMapping("/check")
    public ResponseVO check(String username) {

        if (StringUtils.isNotBlank(username)) {
            boolean notExists = userAPI.checkUsername(username);
            if (notExists) {
                // 返回 true，表示用户名可用
                return ResponseVO.success("用户名不存在");
            } else {
                return ResponseVO.serviceFail("用户名已存在");
            }
        } else {
            return ResponseVO.serviceFail("用户名不能为空");
        }
    }

    @GetMapping("/logout")
    public ResponseVO logout() {
        /*
            应用：
                1. 前端存储 JWT【七天】： JWT 自动刷新
                2. 服务器会存储活动用户信息【30min】
                3. 活跃用户查找: JWT里的 userId 为 key
            退出：
                1. 前端删除 JWT
                2. 后端服务器删除活跃用户缓存
            现状：
                1. 前端删除 JWT
         */

        return ResponseVO.success("用户退出成功");
    }

    @GetMapping("/getUserInfo")
    public ResponseVO getUserInfo() {

        // 获取当前登录用户
        String userId = CurrentUser.getCurrentUser();
        if (StringUtils.isNotBlank(userId)) {

            // 查询用户信息
            int uuid = Integer.parseInt(userId);
            UserInfoModel userInfo = userAPI.getUserInfo(uuid);
            if (userInfo != null) {
                return ResponseVO.success(userInfo);
            } else {
                return ResponseVO.serviceFail("用户信息查询失败");
            }
        } else {
            return ResponseVO.appFail("用户未登录");
        }
    }

    @PostMapping("/updateUserInfo")
    public ResponseVO updateUserInfo(UserInfoModel userInfoModel) {

        // 获取当前登录用户
        String userId = CurrentUser.getCurrentUser();
        if (StringUtils.isNotBlank(userId)) {

            // 判断当前登录用户的Id与修改的结果Id是否一直
            if (Integer.parseInt(userId) != userInfoModel.getUuid()) {
                return ResponseVO.serviceFail("请修改您个人的信息");
            }

            int uuid = Integer.parseInt(userId);
            UserInfoModel userInfo = userAPI.updateUserInfo(userInfoModel);
            if (userInfo != null) {
                return ResponseVO.success(userInfo);
            } else {
                return ResponseVO.serviceFail("用户信息修改失败");
            }
        } else {
            return ResponseVO.appFail("用户未登录");
        }
    }
}

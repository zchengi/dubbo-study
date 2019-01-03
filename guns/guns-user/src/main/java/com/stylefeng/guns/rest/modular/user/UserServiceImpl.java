package com.stylefeng.guns.rest.modular.user;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.api.UserAPI;
import com.stylefeng.guns.api.UserInfoModel;
import com.stylefeng.guns.api.UserModel;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.persistence.dao.ChengUserTMapper;
import com.stylefeng.guns.rest.persistence.model.ChengUserT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author cheng
 *         2019/1/2 20:36
 */
@Slf4j
@Component
@Service(interfaceClass = UserAPI.class)
public class UserServiceImpl implements UserAPI {

    @Autowired
    private ChengUserTMapper chengUserTMapper;

    @Override
    public boolean register(UserModel userModel) {

        // 注册信息实体 -> 数据实体
        ChengUserT chengUserT = new ChengUserT();
        chengUserT.setUserName(userModel.getUsername());
        chengUserT.setEmail(userModel.getEmail());
        chengUserT.setAddress(userModel.getAddress());
        chengUserT.setUserPhone(userModel.getPhone());
        // 创建时间&修改时间 -> current_timestamp

        // 数据加密  [MD5 + salt] (shiro)
        chengUserT.setUserPwd(MD5Util.encrypt(userModel.getPassword()));

        // 数据实体 -> 数据库
        Integer insert = chengUserTMapper.insert(chengUserT);
        return insert > 0;
    }

    @Override
    public int login(String username, String password) {

        // 帐号密码 -> 用户信息
        ChengUserT chengUserT = new ChengUserT();

        chengUserT.setUserName(username);
        ChengUserT result = chengUserTMapper.selectOne(chengUserT);

        // 密码校验
        if (result != null && result.getUuid() > 0) {
            String md5Password = MD5Util.encrypt(password);
            if (result.getUserPwd().equals(md5Password)) {
                return result.getUuid();
            }
        }

        return 0;
    }

    @Override
    public boolean checkUsername(String username) {

        EntityWrapper<ChengUserT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_name", username);
        Integer count = chengUserTMapper.selectCount(entityWrapper);

        // 用户存在
        return count == null || count <= 0;// 用户不存在
    }

    @Override
    public UserInfoModel getUserInfo(int uuid) {
        // uuid -> ChengUserT -> UserInfoMode
        return UserT2UserInfoModel(chengUserTMapper.selectById(uuid));
    }

    @Override
    public UserInfoModel updateUserInfo(UserInfoModel userInfoModel) {

        ChengUserT chengUserT = new ChengUserT();

        // DO -> ChengUserT
        chengUserT.setUuid(userInfoModel.getUuid());
        chengUserT.setNickName(userInfoModel.getNickname());
        chengUserT.setLifeState(Integer.parseInt(userInfoModel.getLifeState()));
        chengUserT.setBirthday(userInfoModel.getBirthday());
        chengUserT.setBiography(userInfoModel.getBiography());
        chengUserT.setHeadUrl(userInfoModel.getHeadAddress());
        chengUserT.setEmail(userInfoModel.getEmail());
        chengUserT.setAddress(userInfoModel.getAddress());
        chengUserT.setUserPhone(userInfoModel.getPhone());
        chengUserT.setUserSex(userInfoModel.getSex());
//        chengUserT.setBeginTime(null);
//        chengUserT.setUpdateTime(null);

        // DO 存入数据库
        Integer update = chengUserTMapper.updateById(chengUserT);

        return update > 0 ? getUserInfo(chengUserT.getUuid()) : null;
    }

    private UserInfoModel UserT2UserInfoModel(ChengUserT chengUserT) {

        UserInfoModel userInfoModel = new UserInfoModel();

        userInfoModel.setUuid(chengUserT.getUuid());
        userInfoModel.setHeadAddress(chengUserT.getHeadUrl());
        userInfoModel.setPhone(chengUserT.getUserPhone());
        userInfoModel.setUpdateTime(chengUserT.getUpdateTime().getTime());
        userInfoModel.setEmail(chengUserT.getEmail());
        userInfoModel.setUsername(chengUserT.getUserName());
        userInfoModel.setNickname(chengUserT.getNickName());
        userInfoModel.setLifeState("" + chengUserT.getLifeState());
        userInfoModel.setBirthday(chengUserT.getBirthday());
        userInfoModel.setAddress(chengUserT.getAddress());
        userInfoModel.setSex(chengUserT.getUserSex());
        userInfoModel.setBeginTime(chengUserT.getBeginTime().getTime());
        userInfoModel.setBiography(chengUserT.getBiography());

        return userInfoModel;
    }

    private Date time2Date(long time) {
        return new Date(time);
    }
}

package com.stylefeng.guns.core.util;

import java.util.UUID;

/**
 * @author cheng
 *         2019/1/15 18:10
 */
public class UUIDUtil {
    public static String generateUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}

package com.ltz.flyfun.parser;

import com.ltz.flyfun.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @包名: com.ltz.flyfun.parser
 * @类名: LogUtil
 * @描述: 日志类
 * @作者: Li Z
 * @日期: 2024/1/23 18:50
 * @版本: 1.0
 */
public class LogUtil {

    private static final Logger logger = LoggerFactory.getLogger(LogUtil.class);

    /**
     * 打印方法
     * @param message
     * @param args
     */
    public static void logInfo(String message, String args) {
        if (Utils.isNullOrEmpty(message) && Utils.isNullOrEmpty(args))
            return;
        if (Utils.isNullOrEmpty(args)) {
            logger.info(message);
        } else {
            logger.info(Utils.isNullOrEmpty(message) ? args : String.format((Utils.isNullOrEmpty(message) ? "" : message + " -> ") + "%s", args));
        }
    }
}

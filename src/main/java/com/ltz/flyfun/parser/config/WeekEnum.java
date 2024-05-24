package com.ltz.flyfun.parser.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
  * @包名: com.ltz.flyfun.parser.config
  * @类名: WeekEnum
  * @描述: 星期枚举类
  * @作者: Li Z
  * @日期: 2024/1/22 19:16
  * @版本: 1.0
 */
@Getter
@AllArgsConstructor
public enum WeekEnum {
    MONDAY(0, "周一"),
    Tuesday(1, "周二"),
    Wednesday(2, "周三"),
    Thursday(3, "周四"),
    Friday(4, "周五"),
    Saturday(5, "周六"),
    Sunday(6, "周日");
    private int index;
    private String content;
}


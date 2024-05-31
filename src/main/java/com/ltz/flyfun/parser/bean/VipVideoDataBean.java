package com.ltz.flyfun.parser.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**

 * @date 2024/2/21 16:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VipVideoDataBean implements Serializable {
    private String title;
    private String imgUrl;
    private String introduction;
    private String dramaIntroduction;
    private List<DramasItem> dramasItemList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DramasItem implements Serializable {
        private String title; // 集数标题
        private String url; // 集数地址
        private int index; // 下标
        private boolean selected; // 是否选中
    }
}

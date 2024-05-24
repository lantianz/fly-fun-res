package com.ltz.flyfun.parser.bean;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
  * @包名: com.ltz.flyfun.parser.bean
  * @类名: VodDataBean
  * @描述: 影视数据实体
  * @日期: 2024/1/23 22:13
  * @版本: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VodDataBean implements Serializable {

    private List<Item> itemList = new ArrayList<>();

    /**
      * @包名: com.ltz.flyfun.parser.bean
      * @类名: VodDataBean.Item
      * @描述: 影视数据列表实体
      * @作者: Li Z
      * @日期: 2024/1/23 22:14
      * @版本: 1.0
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private String title; // 标题
        private String img; // 图片
        private String url; // 地址
        private String topLeftTag; // 左上角TAG
        private String episodesTag; // 图片底部TAG 一般为集数

        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}

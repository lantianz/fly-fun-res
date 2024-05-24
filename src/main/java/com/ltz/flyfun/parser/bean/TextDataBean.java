package com.ltz.flyfun.parser.bean;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
  * @包名: com.ltz.flyfun.parser.bean
  * @类名: TextDataBean 用于{@link }适配器
  * @描述: 纯文本列表实体 一般用于排行榜列表等
  * @日期: 2024/1/26 9:52
  * @版本: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextDataBean {
    /**
     * 展开标题
     */
    private String title;
    /**
     * 列表数据
     */
    private List<Item> itemList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        /**
         * 序号下标
         */
        private String index;
        /**
         * 标题
         */
        private String title;
        /**
         * 访问地址
         */
        private String url;
        /**
         * 左侧内容 暂定为集数，可自行匹配
         */
        private String episodes;
        /**
         * 右侧内容 可自行匹配
         */
        private String content;

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

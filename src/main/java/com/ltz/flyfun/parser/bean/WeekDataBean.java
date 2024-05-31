package com.ltz.flyfun.parser.bean;

import com.alibaba.fastjson.JSONObject;
import com.ltz.flyfun.parser.config.WeekEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
  * @包名: com.ltz.flyfun.parser.bean
  * @类名: WeekDataBean
  * @描述: 星期时间表数据实体
  * @日期: 2024/1/22 19:16
  * @版本: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeekDataBean implements Serializable {
    /**
     * 星期 使用{@link WeekEnum}枚举定义
     */
    private int weekDay;
    /**
     * 每个星期下的数据列表
     */
    private List<WeekItem> weekItems;

    /**
      * @包名: com.ltz.flyfun.parser.bean
      * @类名: WeekDataBean.WeekItem
      * @描述: 星期时间表数据 子数据实体
      * @日期: 2024/1/22 19:18
      * @版本: 1.0
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeekItem implements Serializable {
        /**
         * 剧集标题
         */
        private String title;
        /**
         * 剧集图片
         */
        private String img;
        /**
         * 剧集访问地址
         */
        private String url;
        /**
         * 集数标题
         */
        private String episodes;
        /**
         * 集数地址 暂未支持跳转
         */
        private String episodesUrl;

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

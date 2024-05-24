package com.ltz.flyfun.parser.bean;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
  * @包名: com.ltz.flyfun.parser.bean
  * @类名: MainDataBean
  * @描述: APP首页数据实体
  * @日期: 2024/1/23 22:12
  * @版本: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainDataBean implements Serializable {
    public static final int TAG_LIST = 0; // 首页TAG
    public static final int BANNER_LIST = 1; // 轮播
    public static final int ITEM_LIST = 2; // 剧集列表 横向列表
    public static final int ITEM_SINGLE_LINE_LIST = 3; // 单条数据列表 纵向
    private String title; // 标题
    private String url; // 地址
    private boolean hasMore; // 是否有更多
    private String more; // 更多跳转地址
//    private Class openMoreClass; // 打开更多时的视图class 默认为分类视图
    private int dataType; // 布局
    private int vodItemType = Item.ITEM_TYPE_0; // item布局类型
    private List<Item> items; // 子列表数据
    private List<Tag> tags; // 头部分类数据

    /**
      * @包名: com.ltz.flyfun.parser.bean
      * @类名: MainDataBean.Tag
      * @描述: 头部分类数据列表实体
      * @日期: 2024/1/23 22:12
      * @版本: 1.0
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Tag implements Serializable {
        private String title; // 标题
        private String url; // 地址
        private int img; // 图标 预留

        public Tag(String title, String url) {
            this.title = title;
            this.url = url;
        }

        @Override
        public String toString() {
            return JSONObject.toJSONString(this);
        }
    }

    /**
      * @包名: com.ltz.flyfun.parser.bean
      * @类名: MainDataBean.Item
      * @描述: 首页banner/视频数据列表通用实体
      * @作者: Li Z
      * @日期: 2024/1/23 22:12
      * @版本: 1.0
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item implements Serializable {
        // 如需要更多布局类型 需要自行定义实现，默认仅提供两种
        public static final int ITEM_TYPE_0 = 0; // 布局类型 宽<高[类似：1:1.4]
        public static final int ITEM_TYPE_1 = 1; // 布局类型 宽>高[类似：16:9]
        public static final int ITEM_TYPE_2 = 2; // 布局类型 宽>高[类似：2:1]
        private String title; // 剧集标题
        private String img; // 图片地址
        private String url; // 访问地址
        private String episodes; // 集数
        private String episodesUrl; // 集数跳转地址 -> 一般为播放地址

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

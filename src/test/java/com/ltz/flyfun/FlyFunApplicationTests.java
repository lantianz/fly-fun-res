package com.ltz.flyfun;

import com.ltz.flyfun.parser.bean.MainDataBean;
import com.ltz.flyfun.parser.config.SourceEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class FlyFunApplicationTests {


    public static String s = "http://www.iyinghua.io";
    /**
     * 首页TAG枚举
     */
    @Getter
    @AllArgsConstructor
    public enum HomeTagEnum {
        WEEK("番剧时间表", "%s"),
        DMFL("动漫分类", SourceEnum.I_YINGHUA.getClassificationUrl()),
        DMDY("动漫电影", "/movie/%s"),
        DMZT("动漫专题", "/topic/%s"),
        ZJGX("最新更新", "%s/new/");

        private String name;
        private String content;
    }

    public void get(String url) {
        RestTemplate restTemplate = new RestTemplate();
        parserPageCount(restTemplate.getForObject(url, String.class));
    }


    public static void parserPageCount(String source) {
//        System.out.println(source);
        Document document = Jsoup.parse(source);
        List<MainDataBean> mainDataBeans = new ArrayList<>();
        MainDataBean mainDataBean;
        // tag内容解析
        mainDataBean = new MainDataBean();
        mainDataBean.setDataType(MainDataBean.TAG_LIST);
        List<MainDataBean.Tag> tags = new ArrayList<>();
        tags.add(new MainDataBean.Tag(HomeTagEnum.WEEK.name, HomeTagEnum.WEEK.content));
        tags.add(new MainDataBean.Tag(HomeTagEnum.DMFL.name, HomeTagEnum.DMFL.content));
        tags.add(new MainDataBean.Tag(HomeTagEnum.DMDY.name, HomeTagEnum.DMDY.content));
        tags.add(new MainDataBean.Tag(HomeTagEnum.DMZT.name, HomeTagEnum.DMZT.content));
        tags.add(new MainDataBean.Tag(HomeTagEnum.ZJGX.name, HomeTagEnum.ZJGX.content));
        mainDataBean.setTags(tags);
        mainDataBeans.add(mainDataBean);
        // banner内容解析
        Elements bannerEle = document.select("div.hero-wrap > ul.heros > li");
        mainDataBean = new MainDataBean();
        mainDataBean.setTitle("动漫推荐");
        mainDataBean.setHasMore(false);
        mainDataBean.setDataType(MainDataBean.BANNER_LIST);
        mainDataBean.setVodItemType(MainDataBean.Item.ITEM_TYPE_1);
        List<MainDataBean.Item> items = new ArrayList<>();
        for (Element element : bannerEle) {
            MainDataBean.Item item = new MainDataBean.Item();
            item.setTitle(element.select("a").attr("title"));
            item.setUrl(element.select("a").attr("href"));
            item.setImg(element.select("img").attr("src"));
            item.setEpisodes(element.getElementsByTag("em").text());
            items.add(item);
        }
        mainDataBean.setItems(items);
        mainDataBeans.add(mainDataBean);
        // 数据列表解析
        Elements titles = document.select("div.firs > div.dtit");
        Elements data = document.select("div.firs > div.img");
        for (int i=0,size=titles.size(); i<size; i++) {
            mainDataBean = new MainDataBean();
            mainDataBean.setTitle(titles.get(i).select("h2 > a").text());
            mainDataBean.setHasMore(i != 0);
            String moreUrl = titles.get(i).select("h2 > a").attr("href");
            mainDataBean.setMore(moreUrl);
//                if (HomeTagEnum.DMDY.content.contains(moreUrl)) // 如果是电影 则应该返回视频列表视图
//                    mainDataBean.setOpenMoreClass(VodListActivity.class);
            mainDataBean.setDataType(MainDataBean.ITEM_LIST);
            items = new ArrayList<>();
            Elements vodLi = data.get(i).select("ul > li");
            for (Element li : vodLi) {
                Elements aList = li.select("a");
                MainDataBean.Item item = new MainDataBean.Item();
                item.setTitle(aList.get(1).text());
                item.setUrl(aList.get(1).attr("href"));
                item.setImg(aList.get(0).select("img").attr("src"));
                item.setEpisodes(aList.size() == 3 ? aList.get(2).text() : "");
                items.add(item);
            }
            if (items.size() > 0) {
                mainDataBean.setItems(items);
                mainDataBeans.add(mainDataBean);
            }
        }
        System.out.println(mainDataBeans);

    }

    @Test
    public void contextLoads() {
        get(s);
    }
}

package com.ltz.flyfun.controller;

import com.ltz.flyfun.contract.ClassificationVodListCallback;
import com.ltz.flyfun.model.ClassificationVodListModel;
import com.ltz.flyfun.parser.bean.ClassificationDataBean;
import com.ltz.flyfun.parser.bean.VodDataBean;
import com.ltz.flyfun.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;


@RestController
@RequestMapping("/api/classificationVodList")
public class ClassificationVodListController {
    ClassificationVodListModel classificationVodListModel = new ClassificationVodListModel();
    HashMap res = new HashMap();

    @GetMapping("/get")
    public Result<?> get(@RequestParam("id") String id,
                         @RequestParam("type") String type,
                         @RequestParam("area") String area,
                         @RequestParam("year") String year,
                         @RequestParam("lang") String lang,
                         @RequestParam("latter") String latter,
                         @RequestParam("by") String by,
                         @RequestParam("page") String page) throws UnsupportedEncodingException {
        CountDownLatch latch = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(1);

        boolean firstTimeData = true;
        String[] params = new String[classificationVodListModel.getParamsSize()];
        // 筛选页面参数
        /*
          params[0] id -> 0
          params[1] type -> 1
          params[2] area -> 4
          params[3] year -> 3
          // 拼接
          params[4] lang -> 6
          params[5] latter -> 2
          params[6] by -> 5
          params[7] page -> 7
            */
        // 头部tag参数
        params[0] = id;
        params[1] = type;
        params[2] = area;
        params[3] = year;
        params[4] = lang;
        params[5] = latter;
        params[6] = by;
        params[params.length - 1] = page;

        classificationVodListModel.getData(firstTimeData, new ClassificationVodListCallback.DataCallback() {
            @Override
            public void successClassList(List<ClassificationDataBean> classificationDataBeans) {
                try {
                    res.put("classificationDataBeans", classificationDataBeans);
                } finally {
                    latch.countDown();
                }
            }

            @Override
            public void errorClassList(String msg) {
                try {
                    res.put("msg", msg);
                } finally {
                    latch.countDown();
                }
            }

            @Override
            public void emptyClassList() {

            }

            @Override
            public void successVodList(boolean firstTimeData, VodDataBean vodDataBean, int pageCount) {
                try {
                    res.clear();
                    res.put("firstTimeData", firstTimeData);
                    res.put("vodDataBean", vodDataBean);
                    res.put("pageCount", pageCount);
                } finally {
                    latch2.countDown();
                }
            }

            @Override
            public void errorVodList(boolean firstTimeData, String msg) {
                try {
                    res.clear();
                    res.put("firstTimeData", firstTimeData);
                    res.put("msg", msg);
                } finally {
                    latch2.countDown();
                }
            }

            @Override
            public void emptyVodList(String msg) {
                try {
                    res.clear();
                    res.put("msg", msg);
                } finally {
                    latch2.countDown();
                }
            }

            @Override
            public void onFailure(String msg) {
                try {
                    res.clear();
                    res.put("msg", msg);
                } finally {
                    latch.countDown();
                }
            }
        }, params);
        try {
            latch.await();
            latch2.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success(res);
    }
}

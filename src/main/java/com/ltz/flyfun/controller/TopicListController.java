package com.ltz.flyfun.controller;

import com.ltz.flyfun.contract.TopicListCallback;
import com.ltz.flyfun.model.TopicListModel;
import com.ltz.flyfun.parser.bean.VodDataBean;
import com.ltz.flyfun.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/api/topicList")
public class TopicListController {
    TopicListModel topicListModel = new TopicListModel();
    HashMap res = new HashMap();

    @GetMapping("/get")
    public Result<?> get() throws UnsupportedEncodingException {
        CountDownLatch latch = new CountDownLatch(1);
        String url = "/vodtype/";
        boolean isVodList = true;
        int page = 3;

        topicListModel.getData(true, url, isVodList, page, new TopicListCallback.DataCallback() {

            @Override
            public void success(boolean firstTimeData, VodDataBean vodDataBean, int pageCount) {
                try {
                    res.clear();
                    res.put("firstTimeData", firstTimeData);
                    res.put("vodDataBean", vodDataBean);
                    res.put("pageCount", pageCount);
                } finally {
                    latch.countDown();
                }
            }

            @Override
            public void error(boolean firstTimeData, String msg) {
                try {
                    res.clear();
                    res.put("msg", msg);
                    res.put("msg", msg);
                } finally {
                    latch.countDown();
                }
            }

            @Override
            public void empty(String msg) {
                try {
                    res.clear();
                    res.put("msg", msg);
                } finally {
                    latch.countDown();
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
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success(res);
    }
}

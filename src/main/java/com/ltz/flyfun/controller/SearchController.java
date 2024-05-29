package com.ltz.flyfun.controller;

import com.ltz.flyfun.contract.SearchCallback;
import com.ltz.flyfun.model.SearchModel;
import com.ltz.flyfun.parser.bean.VodDataBean;
import com.ltz.flyfun.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    SearchModel searchModel = new SearchModel();
    HashMap res = new HashMap();

    @GetMapping("/get")
    public Result<?> get(@RequestParam("keyword") String keyword, @RequestParam("page") String page) throws UnsupportedEncodingException {
        CountDownLatch latch = new CountDownLatch(1);

        String[] params = new String[searchModel.getParamsSize()];

        params[1] = page;
        params[0] = keyword;  // 固定格式：下标0为搜索参数 1为页码

        searchModel.getData(true, new SearchCallback.DataCallback() {

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
                    res.put("firstTimeData", firstTimeData);
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
        }, params);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success(res);
    }
}

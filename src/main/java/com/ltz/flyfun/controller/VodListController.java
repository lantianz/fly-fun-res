package com.ltz.flyfun.controller;

import com.ltz.flyfun.contract.VodListCallback;
import com.ltz.flyfun.model.VodListModel;
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
@RequestMapping("/api/vodList")
public class VodListController {
    VodListModel vodListModel = new VodListModel();
    HashMap res = new HashMap();

    @GetMapping("/get")
    public Result<?> get(@RequestParam("url") String url, @RequestParam("page") int page) throws UnsupportedEncodingException {
        CountDownLatch latch = new CountDownLatch(1);

//        String url = "/search/actor/%E4%B8%8B%E9%87%8E%E7%BA%AE/page/replaceThis.html"; // anime详情页tag点击
//        int page = 2;

        vodListModel.getData(true, url, page, new VodListCallback.DataCallback() {

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
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.success(res);
    }
}

package com.ltz.flyfun.controller;

import com.alibaba.fastjson.JSONObject;
import com.ltz.flyfun.contract.DanmuCallback;
import com.ltz.flyfun.model.DanmuModel;
import com.ltz.flyfun.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/api/danmu")
public class DanmuController {
    DanmuModel danmuModel = new DanmuModel();
    HashMap res = new HashMap();

    @GetMapping("/get")
    public Result<?> get() {
        CountDownLatch latch = new CountDownLatch(1);

        String[] params = new String[danmuModel.getParamsSize()];
        params[0] = "1801";  // 视频ID
//        params[1] = "1";    // 集数下标

        danmuModel.getDanmu(new DanmuCallback.DataCallback() {
            @Override
            public void successDanmuJson(JSONObject danmus) {
                try {
                    res.clear();
                    res.put("danmus", danmus);
                } finally {
                    latch.countDown();
                }
            }

            @Override
            public void successDanmuXml(String content) {
                try {
                    res.clear();
                    res.put("content", content);
                } finally {
                    latch.countDown();
                }
            }

            @Override
            public void errorDanmu(String msg) {
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

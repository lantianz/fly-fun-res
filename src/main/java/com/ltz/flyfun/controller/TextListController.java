package com.ltz.flyfun.controller;

import com.ltz.flyfun.contract.TextListCallback;
import com.ltz.flyfun.model.TextListModel;
import com.ltz.flyfun.parser.bean.TextDataBean;
import com.ltz.flyfun.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/api/textList")
public class TextListController {
    TextListModel textListModel = new TextListModel();
    HashMap res = new HashMap();

    @GetMapping("/get")
    public Result<?> get(@RequestParam("id") String id) {
        CountDownLatch latch = new CountDownLatch(1);

        textListModel.getData(id, new TextListCallback.DataCallback() {

            @Override
            public void success(List<TextDataBean> textDataBeans) {
                try {
                    res.clear();
                    res.put("textDataBeans", textDataBeans);
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

package com.ltz.flyfun.controller;

import com.ltz.flyfun.contract.HomeCallback;
import com.ltz.flyfun.model.HomeModel;
import com.ltz.flyfun.parser.bean.MainDataBean;
import com.ltz.flyfun.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/api/home")
public class HomeController {
    HomeModel homeModel = new HomeModel();
    HashMap res = new HashMap();

    @GetMapping("/get")
    public Result<?> get() {
        CountDownLatch latch = new CountDownLatch(1);
        homeModel.getData(new HomeCallback.DataCallback() {

            @Override
            public void success(List<MainDataBean> mainDataBeans) {
                try {
                    res.clear();
                    res.put("mainDataBeans", mainDataBeans);
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

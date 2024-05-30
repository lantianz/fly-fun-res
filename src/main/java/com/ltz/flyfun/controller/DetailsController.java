package com.ltz.flyfun.controller;

import com.ltz.flyfun.contract.DetailsCallback;
import com.ltz.flyfun.model.DetailsModel;
import com.ltz.flyfun.parser.bean.DetailsDataBean;
import com.ltz.flyfun.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/api/details")
public class DetailsController {
    DetailsModel detailsModel = new DetailsModel();
    HashMap res = new HashMap();

    @GetMapping("/get")
    public Result<?> get(@RequestParam("from") String from, @RequestParam("url") String urlAccept) {
        CountDownLatch latch = new CountDownLatch(1);

        String url = "";
        // video页面需要
        if (from == null || from.equals("")) {
            url = urlAccept;
        } else {
            url = from;
        }

        detailsModel.getData(url, new DetailsCallback.DataCallback() {
            @Override
            public void success(DetailsDataBean detailsDataBean) {
                try {
                    res.clear();
                    res.put("detailsDataBean", detailsDataBean);
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

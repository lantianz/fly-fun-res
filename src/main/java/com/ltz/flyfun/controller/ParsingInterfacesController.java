package com.ltz.flyfun.controller;

import com.ltz.flyfun.contract.ParsingInterfacesCallback;
import com.ltz.flyfun.model.ParsingInterfacesModel;
import com.ltz.flyfun.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/api/parsingInterfaces")
public class ParsingInterfacesController {
    ParsingInterfacesModel parsingInterfacesModel = new ParsingInterfacesModel();
    HashMap res = new HashMap();

    @GetMapping("/get")
    public Result<?> get() {
        CountDownLatch latch = new CountDownLatch(1);

        String url = "";    // 暂时不知道干嘛的

        parsingInterfacesModel.parser(url, new ParsingInterfacesCallback.DataCallback() {

            @Override
            public void success(Object object) {
                try {
                    res.clear();
                    res.put("object", object);
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

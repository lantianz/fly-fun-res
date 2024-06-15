package com.ltz.flyfun.controller;

import com.ltz.flyfun.contract.WeekCallback;
import com.ltz.flyfun.model.WeekModel;
import com.ltz.flyfun.parser.bean.WeekDataBean;
import com.ltz.flyfun.parser.config.ParserInterfaceFactory;
import com.ltz.flyfun.parser.config.SourceEnum;
import com.ltz.flyfun.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/api/week")
public class WeekController {
    WeekModel weekModel = new WeekModel();
    HashMap res = new HashMap();

    @GetMapping("/get")
    public Result<?> get() {
        CountDownLatch latch = new CountDownLatch(1);

        String url = SourceEnum.getDomainUrlBySource(ParserInterfaceFactory.USER_SOURCE);

        weekModel.getWeekData(url, new WeekCallback.DataCallback() {

            @Override
            public void weekSuccess(List<WeekDataBean> weekDataBeans) {
                try {
                    res.clear();
                    res.put("weekDataBeans", weekDataBeans);
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

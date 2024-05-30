package com.ltz.flyfun.controller;

import com.ltz.flyfun.contract.VideoCallback;
import com.ltz.flyfun.model.VideoModel;
import com.ltz.flyfun.parser.bean.DetailsDataBean;
import com.ltz.flyfun.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/api/video")
public class VideoController {
    VideoModel videoModel = new VideoModel();
    HashMap res = new HashMap();

    @GetMapping("/get")
    public Result<?> get(@RequestParam("url") String url) {
        CountDownLatch latch = new CountDownLatch(1);

        boolean onlyGetPlayUrl = false;
//        String url = "/play/1861-1-6.html";
        int sourceIndex = 0;    // 播放列表下标（但是没有用到，所以不用管）

        videoModel.getData(onlyGetPlayUrl, url, sourceIndex, new VideoCallback.DataCallback() {
            @Override
            public void errorNet(String msg) {
                try {
                    res.clear();
                    res.put("msg", msg);
                } finally {
                    latch.countDown();
                }
            }

            @Override
            public void successPlayUrl(List<String> urls) {
                try {
                    res.put("urls", urls);
                } finally {
                    latch.countDown();
                }
            }

            @Override
            public void errorPlayUrl() {

            }

            @Override
            public void successDramasList(List<DetailsDataBean.Dramas> dramasList) {
                try {
                    res.clear();
                    res.put("dramasList", dramasList);
                } finally {
                    latch.countDown();
                }
            }

            @Override
            public void errorDramasList() {

            }

            @Override
            public void successOnlyPlayUrl(List<String> urls) {
                try {
                    res.put("urls", urls);
                } finally {
                    latch.countDown();
                }
            }

            @Override
            public void errorOnlyPlayUrl() {

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

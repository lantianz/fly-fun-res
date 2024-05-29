package com.ltz.flyfun.controller;

import com.ltz.flyfun.contract.UpdateImgCallback;
import com.ltz.flyfun.model.UpdateImgModel;
import com.ltz.flyfun.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/api/updateImg")
public class UpdateImgController {
    UpdateImgModel updateImgModel = new UpdateImgModel();
    HashMap res = new HashMap();

    @GetMapping("/get")
    public Result<?> get() {
        CountDownLatch latch = new CountDownLatch(1);

        String oldImgUrl = "https://static-cdn.anfuns.cn/upload/vod/20240517-1/fd7bdb5aea140e6de27e126c3f6b39f3.jpg";
        String descUrl = "https://static-cdn.anfuns.cn/upload/vod/20220206-1/38ef258c94514f4fb315bf2a06d23649.jpg";

        updateImgModel.getImg(oldImgUrl, descUrl, new UpdateImgCallback.DataCallback() {

            @Override
            public void successImg(String oldImgUrl, String imgUrl) {
                try {
                    res.clear();
                    res.put("oldImgUrl", oldImgUrl);
                    res.put("imgUrl", imgUrl);
                } finally {
                    latch.countDown();
                }
            }

            @Override
            public void errorImg() {

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

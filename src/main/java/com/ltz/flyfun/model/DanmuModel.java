package com.ltz.flyfun.model;

import com.alibaba.fastjson.JSONObject;
import com.ltz.flyfun.contract.DanmuCallback;
import com.ltz.flyfun.net.OkHttpUtils;
import com.ltz.flyfun.utils.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;

/**
 * 弹幕
 */
public class DanmuModel extends BaseModel {
    public int getParamsSize() {
        return parserInterface.setClassificationParamsSize();
    }
    public int getStartPageNum() {
        return parserInterface.startPageNum();
    }
    public void getDanmu(DanmuCallback.DataCallback callback, String... params) {
        String url = parserInterface.getDanmuUrl(params);
        if (Utils.isNullOrEmpty(url)) {
            callback.onFailure("url为空：可能没有弹幕");
            return;
        }
        if (parserInterface.getPostMethodClassName().contains(this.getClass().getName())) {
            // 使用http post
        } else
            OkHttpUtils.getInstance().doGet(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.errorDanmu(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String danmu = response.body().string();
                        if (Utils.isNullOrEmpty(danmu))
                            callback.errorDanmu("获取弹幕信息失败");
                        else {
                            if (parserInterface.getDanmuResultJson()) {
                                // 这里是返回JSON，需要自己实现JSON处理 示例：
                                try {
                                    JSONObject jsonObject = JSONObject.parseObject(danmu);
                                    if (jsonObject.getInteger("code") == 200)
                                        callback.successDanmuJson(jsonObject);
                                    else
                                        callback.errorDanmu("接口服务返回异常，请稍后再试！");
                                } catch (Exception e) {
                                    callback.errorDanmu("弹幕接口返回JSON格式异常，内容解析失败！");
                                }
                            } else {
                                // 返回XML
                                callback.successDanmuXml(danmu);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.errorDanmu(e.getMessage());
                    }
                }
            });
    }
}

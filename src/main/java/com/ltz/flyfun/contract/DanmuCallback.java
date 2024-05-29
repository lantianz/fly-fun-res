package com.ltz.flyfun.contract;

import com.alibaba.fastjson.JSONObject;

public interface DanmuCallback {
    interface DataCallback {
        void successDanmuJson(JSONObject danmus);

        void successDanmuXml(String content);

        void errorDanmu(String msg);

        void onFailure(String msg);
    }

}

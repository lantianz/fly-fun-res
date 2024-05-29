package com.ltz.flyfun.contract;

import com.ltz.flyfun.parser.bean.TextDataBean;

import java.util.List;

public interface TextListCallback {
    interface DataCallback {
        void success(List<TextDataBean> textDataBeans);

        void empty(String msg);

        void onFailure(String msg);
    }
}

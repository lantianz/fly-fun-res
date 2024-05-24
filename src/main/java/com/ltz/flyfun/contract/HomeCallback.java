package com.ltz.flyfun.contract;

import com.ltz.flyfun.parser.bean.MainDataBean;

import java.util.List;

public interface HomeCallback {
    interface DataCallback {
        void success(List<MainDataBean> mainDataBeans);

        void onFailure(String msg);
    }
}

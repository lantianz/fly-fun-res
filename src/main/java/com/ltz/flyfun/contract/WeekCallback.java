package com.ltz.flyfun.contract;

import com.ltz.flyfun.parser.bean.WeekDataBean;

import java.util.List;

public interface WeekCallback {
    interface DataCallback {
        void weekSuccess(List<WeekDataBean> weekDataBeans);

        void onFailure(String msg);
    }
}

package com.ltz.flyfun.contract;

import com.ltz.flyfun.parser.bean.VodDataBean;

public interface VodListCallback {
    interface DataCallback {
        void success(boolean firstTimeData, VodDataBean vodDataBean, int pageCount);

        void error(boolean firstTimeData, String msg);

        void empty(String msg);

        void onFailure(String msg);
    }
}

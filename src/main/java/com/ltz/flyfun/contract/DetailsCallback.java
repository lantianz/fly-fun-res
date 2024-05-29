package com.ltz.flyfun.contract;

import com.ltz.flyfun.parser.bean.DetailsDataBean;

public interface DetailsCallback {
    interface DataCallback {
        void success(DetailsDataBean detailsDataBean);

        void onFailure(String msg);
    }
}

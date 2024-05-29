package com.ltz.flyfun.contract;

import com.ltz.flyfun.parser.bean.ClassificationDataBean;
import com.ltz.flyfun.parser.bean.VodDataBean;

import java.util.List;

public interface ClassificationVodListCallback {
    interface DataCallback {
        void successClassList(List<ClassificationDataBean> classificationDataBeans);

        void errorClassList(String msg);

        void emptyClassList();

        void successVodList(boolean firstTimeData, VodDataBean vodDataBean, int pageCount);

        void errorVodList(boolean firstTimeData, String msg);

        void emptyVodList(String msg);

        void onFailure(String msg);
    }
}

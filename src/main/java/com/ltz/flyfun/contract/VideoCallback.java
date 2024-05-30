package com.ltz.flyfun.contract;

import com.ltz.flyfun.parser.bean.DetailsDataBean;

import java.util.List;

public interface VideoCallback {
    interface DataCallback {

        void errorNet(String msg);
        void successPlayUrl(List<String> urls);
        void errorPlayUrl();
        void successDramasList(List<DetailsDataBean.Dramas> dramasItems);
        void errorDramasList();

        void successOnlyPlayUrl(List<String> urls);

        void errorOnlyPlayUrl();

        void onFailure(String msg);
    }
}

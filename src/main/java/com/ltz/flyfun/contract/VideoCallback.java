package com.ltz.flyfun.contract;

import java.util.List;

public interface VideoCallback {
    interface DataCallback {

        void errorNet(String msg);

        void successOnlyPlayUrl(List<String> urls);

        void errorOnlyPlayUrl();

        void onFailure(String msg);
    }
}

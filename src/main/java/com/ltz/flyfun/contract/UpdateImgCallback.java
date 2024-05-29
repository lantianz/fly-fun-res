package com.ltz.flyfun.contract;

public interface UpdateImgCallback {
    interface DataCallback {
        void successImg(String oldImgUrl, String imgUrl);

        void errorImg();

        void onFailure(String msg);
    }
}

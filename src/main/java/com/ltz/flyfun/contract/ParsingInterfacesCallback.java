package com.ltz.flyfun.contract;

public interface ParsingInterfacesCallback {
    interface DataCallback {
        void success(Object object);

        void onFailure(String msg);
    }
}

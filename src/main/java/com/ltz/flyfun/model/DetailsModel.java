package com.ltz.flyfun.model;

import com.ltz.flyfun.contract.DetailsCallback;
import com.ltz.flyfun.net.OkHttpUtils;
import com.ltz.flyfun.parser.bean.DetailsDataBean;
import com.ltz.flyfun.utils.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author Li
 * @version 1.0
 * @description: 注释
 * @date 2023/12/31 12:07
 */
public class DetailsModel extends BaseModel {
    public void getData(String url, DetailsCallback.DataCallback callback) {
        url = getHttpUrl(url);
        if (parserInterface.getPostMethodClassName().contains(this.getClass().getName())) {
            // 使用http post
        } else {
            String finalUrl = url;
            OkHttpUtils.getInstance().doGet(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String source = getBody(response);
                        DetailsDataBean detailsDataBean = parserInterface.parserDetails(finalUrl, source);
                        if (Utils.isNullOrEmpty(detailsDataBean)) {
                            callback.onFailure(parserErrorMsg(response, source));
                            return;
                        }
                        callback.success(detailsDataBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailure(e.getMessage());
                    }
                }
            });
        }
    }
}

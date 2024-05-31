package com.ltz.flyfun.model;

import com.ltz.flyfun.contract.HomeCallback;
import com.ltz.flyfun.net.OkHttpUtils;
import com.ltz.flyfun.parser.bean.MainDataBean;
import com.ltz.flyfun.utils.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**

 * @date 2023/12/30 20:59
 */
public class HomeModel extends BaseModel {
    public void getData(HomeCallback.DataCallback callback) {
        if (parserInterface.getPostMethodClassName().contains(this.getClass().getName())) {
            // 使用http post
        } else {
            OkHttpUtils.getInstance().doGet(parserInterface.getDefaultDomain(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String source = getBody(response);
                        List<MainDataBean> mainDataBeans = parserInterface.parserMainData(source);
                        if (!Utils.isNullOrEmpty(mainDataBeans)) {
                            callback.success(mainDataBeans);
                        } else {
                            callback.success(Collections.emptyList());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailure(e.getMessage());
                    }
                }
            });
        }
    }
}

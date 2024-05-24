package com.ltz.flyfun.model;

import com.ltz.flyfun.contract.WeekCallback;
import com.ltz.flyfun.net.OkHttpUtils;
import com.ltz.flyfun.parser.bean.WeekDataBean;
import com.ltz.flyfun.utils.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.List;

public class WeekModel extends BaseModel {
    public void getWeekData(String url, WeekCallback.DataCallback callback) {
        url = getHttpUrl(url);
        if (parserInterface.getPostMethodClassName().contains(this.getClass().getName())) {
            // 使用http post
        } else {
            OkHttpUtils.getInstance().doGet(url, new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    callback.onFailure(e.getMessage());
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    try {
                        String source = getBody(response);
                        List<WeekDataBean> weekDataBeans = parserInterface.parserWeekDataList(source);
                        if (Utils.isNullOrEmpty(weekDataBeans))
                            callback.onFailure(parserErrorMsg(response, source));
                        else if (weekDataBeans.size() > 0)
                            callback.weekSuccess(weekDataBeans);
                        else
                            callback.onFailure("下载失败 →%s");
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailure(e.getMessage());
                    }
                }
            });
        }
    }
}

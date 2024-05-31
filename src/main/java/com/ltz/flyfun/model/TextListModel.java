package com.ltz.flyfun.model;

import com.ltz.flyfun.contract.TextListCallback;
import com.ltz.flyfun.net.OkHttpUtils;
import com.ltz.flyfun.parser.bean.TextDataBean;
import com.ltz.flyfun.utils.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

/**

 * @date 2024/1/24 15:40
 */
public class TextListModel extends BaseModel {
    public void getData(String url, TextListCallback.DataCallback callback) {
        url = parserInterface.getTextUrl(url);
        if (parserInterface.getPostMethodClassName().contains(this.getClass().getName())) {
            // 使用http post
        } else
            OkHttpUtils.getInstance().doGet(url, new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String source = getBody(response);
                        List<TextDataBean> textDataBeans = parserInterface.parserTextList(source);
                        if (Utils.isNullOrEmpty(textDataBeans))
                            callback.onFailure(parserErrorMsg(response, source));
                        else if (textDataBeans.size() > 0)
                            callback.success(textDataBeans);
                        else
                            callback.empty("没有更多数据了");
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailure(e.getMessage());
                    }
                }
            });
    }
}

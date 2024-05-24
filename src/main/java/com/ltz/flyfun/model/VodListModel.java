package com.ltz.flyfun.model;

import com.ltz.flyfun.contract.VodListCallback;
import com.ltz.flyfun.net.OkHttpUtils;
import com.ltz.flyfun.parser.bean.VodDataBean;
import com.ltz.flyfun.utils.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author Li
 * @version 1.0
 * @description: 注释
 * @date 2023/12/31 21:46
 */
public class VodListModel extends BaseModel {
    public void getData(boolean firstTimeData, String url, int page, VodListCallback.DataCallback callback) throws UnsupportedEncodingException {
        url = parserInterface.getVodListUrl(url, page);
        if (parserInterface.getPostMethodClassName().contains(this.getClass().getName())) {
            // 使用http post
        } else
            OkHttpUtils.getInstance().doGet(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.error(firstTimeData, e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String source = getBody(response);
                        VodDataBean vodDataBean = parserInterface.parserVodList(source);
                        int pageCount = firstTimeData ? parserInterface.parserPageCount(source) : parserInterface.startPageNum();
                        if (Utils.isNullOrEmpty(vodDataBean))
                            callback.error(firstTimeData, parserErrorMsg(response, source));
                        else if (vodDataBean.getItemList().size() > 0)
                            callback.success(firstTimeData, vodDataBean, pageCount);
                        else
                            callback.empty("没有更多数据了");
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.error(firstTimeData, e.getMessage());
                    }
                }
            });
    }
}

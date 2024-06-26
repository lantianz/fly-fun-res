package com.ltz.flyfun.model;

import com.ltz.flyfun.contract.SearchCallback;
import com.ltz.flyfun.net.OkHttpUtils;
import com.ltz.flyfun.parser.bean.VodDataBean;
import com.ltz.flyfun.utils.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**

 * @date 2023/12/31 21:46
 */
public class SearchModel extends BaseModel {
    public int getParamsSize() {
        return parserInterface.setClassificationParamsSize();
    }
    public int getStartPageNum() {
        return parserInterface.startPageNum();
    }
    public void getData(boolean firstTimeData, SearchCallback.DataCallback callback, String... param) throws UnsupportedEncodingException {
        String url = parserInterface.getSearchUrl(param);
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
                        VodDataBean vodDataBean = parserInterface.parserSearchVodList(source);
                        int pageCount = firstTimeData ? parserInterface.parserPageCount(source) : parserInterface.startPageNum();
                        if (Utils.isNullOrEmpty(vodDataBean)) {
                            callback.error(firstTimeData, parserErrorMsg(response, source));
                        } else if (vodDataBean.getItemList().size() > 0)
                            callback.success(firstTimeData, vodDataBean, pageCount);
                        else
                            callback.empty(param[0] + "没有更多数据了");
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.error(firstTimeData, e.getMessage());
                    }
                }
            });
    }
}

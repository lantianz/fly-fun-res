package com.ltz.flyfun.model;

import com.ltz.flyfun.contract.ClassificationVodListCallback;
import com.ltz.flyfun.net.OkHttpUtils;
import com.ltz.flyfun.parser.bean.ClassificationDataBean;
import com.ltz.flyfun.parser.bean.VodDataBean;
import com.ltz.flyfun.utils.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author Li
 * @version 1.0
 * @description: 注释
 * @date 2023/12/31 21:46
 */
public class ClassificationVodListModel extends BaseModel {
    public int getParamsSize() {
        return parserInterface.setClassificationParamsSize();
    }
    public int getStartPageNum() {
        return parserInterface.startPageNum();
    }
    public void getData(boolean firstTimeData, ClassificationVodListCallback.DataCallback callback, String... param) throws UnsupportedEncodingException {
        String url = parserInterface.getClassificationUrl(param);
        if (parserInterface.getPostMethodClassName().contains(this.getClass().getName())) {
            // 使用http post
        } else
            OkHttpUtils.getInstance().doGet(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.errorVodList(firstTimeData, e.getMessage());
                    callback.errorClassList(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String source = getBody(response);
                        VodDataBean vodDataBean = parserInterface.parserClassificationVodList(source);
                        int pageCount = firstTimeData ? parserInterface.parserPageCount(source) : parserInterface.startPageNum();
                        if (Utils.isNullOrEmpty(vodDataBean))
                            callback.errorVodList(firstTimeData, parserErrorMsg(response, source));
                        else if (vodDataBean.getItemList().size() > 0)
                            callback.successVodList(firstTimeData, vodDataBean, pageCount);
                        else
                            callback.emptyVodList("无数据");
                        if (firstTimeData) {
                            List<ClassificationDataBean> classificationDataBeans = parserInterface.parserClassificationList(source);
                            if (Utils.isNullOrEmpty(classificationDataBeans))
                                callback.onFailure(parserErrorMsg(response, source));
                            else if (classificationDataBeans.size() > 0)
                                callback.successClassList(classificationDataBeans);
                            else
                                callback.emptyClassList();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.errorVodList(firstTimeData, e.getMessage());
                        callback.errorClassList(e.getMessage());
                    }
                }
            });
    }
}

package com.ltz.flyfun.model;

import com.ltz.flyfun.contract.VideoCallback;
import com.ltz.flyfun.net.OkHttpUtils;
import com.ltz.flyfun.parser.bean.DetailsDataBean;
import com.ltz.flyfun.utils.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

/**
 * @author Li
 * @version 1.0
 * @description: 注释
 * @date 2023/12/31 16:20
 */
public class VideoModel extends BaseModel {
    public void getData(boolean onlyGetPlayUrl, String url, int listSource, VideoCallback.DataCallback callback) {
        if (parserInterface.getPostMethodClassName().contains(this.getClass().getName())) {
            // 使用http post

//            FormBody body = parserInterface.getPostFormBodyByClassName(this.getClass().getName());
//            OkHttpUtils.getInstance().doPost(getHttpUrl(url), body, new Callback() {
//                @Override
//                public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                    callback.errorNet(e.getMessage());
//                }
//
//                @Override
//                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                    try {
//                        String source = getBody(response);
//                        String vodId = TVideoManager.queryId(title);
//                        String dramaStr = THistoryManager.queryAllIndex(vodId, true, listSource);
//                        switch (parserInterface.getSource()) {
//                            case ParserInterfaceFactory.SOURCE_SILISILI:// 嘶哩嘶哩站点视频播放地址解析方案
//                                String decodeData = SilisiliImpl.getDecodeData(source);
//                                if (decodeData.isEmpty())
//                                    callback.errorPlayUrl();
//                                else {
//                                    List<String> urls = parserInterface.getPlayUrl(decodeData);
//                                    if (onlyGetPlayUrl) {
//                                        if (!Utils.isNullOrEmpty(urls))
//                                            callback.successOnlyPlayUrl(urls);
//                                        else
//                                            callback.errorOnlyPlayUrl();
//                                    } else {
//                                        String fenjihtml = SilisiliImpl.getJsonData(false, decodeData);
//                                        List<DetailsDataBean.DramasItem> dramasItems = parserInterface.parserNowSourcesDramas(fenjihtml, listSource, dramaStr);
//                                        if (!Utils.isNullOrEmpty(dramasItems)) {
//                                            for (DetailsDataBean.DramasItem item : dramasItems) {
//                                                if (dramaStr.contains(item.getUrl())) {
//                                                    item.setSelected(true);
//                                                }
//                                            }
//                                            callback.successDramasList(dramasItems);
//                                        }
//                                        else
//                                            callback.errorDramasList();
//                                        if (!Utils.isNullOrEmpty(urls))
//                                            callback.successPlayUrl(urls);
//                                        else
//                                            callback.errorPlayUrl();
//                                    }
//                                }
//                                break;
//                        }
//                    } catch (Exception e) {
//                        callback.errorNet(e.getMessage());
//                    }
//                }
//            });
        } else {
            OkHttpUtils.getInstance().doGet(getHttpUrl(url), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.errorNet(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String source = getBody(response);
                        List<String> urls = parserInterface.getPlayUrl(source);
                        String dramaStr = url;
                        if (!onlyGetPlayUrl) {
//                            List<DetailsDataBean.DramasItem> dramasLi = parserInterface.parserNowSourcesDramas(source, listSource, dramaStr);
                            List<DetailsDataBean.Dramas> dramasList = parserInterface.parserSourcesDramas(source, listSource, dramaStr);
                            if (!Utils.isNullOrEmpty(dramasList)) {
                                for (DetailsDataBean.Dramas dramas : dramasList) {
                                    for (DetailsDataBean.DramasItem dramasItem : dramas.getDramasItemList()) {
                                        if (dramaStr.contains(dramasItem.getUrl())) {
                                            dramasItem.setSelected(true);
                                        }
                                    }
                                }
                                callback.successDramasList(dramasList);    //第一次回调
                            }
                            else
                                callback.errorDramasList();

                            if (!Utils.isNullOrEmpty(urls))
                                callback.successPlayUrl(urls);  //第二次回调
                            else
                                callback.errorPlayUrl();
                        } else {
                            if (!Utils.isNullOrEmpty(urls))
                                callback.successOnlyPlayUrl(urls);
                            else
                                callback.errorOnlyPlayUrl();
                        }
                    } catch (Exception e) {
                        callback.errorNet(e.getMessage());
                    }
                }
            });
        }
    }

}

package com.ltz.flyfun.model;

import com.ltz.flyfun.contract.UpdateImgCallback;
import com.ltz.flyfun.net.OkHttpUtils;
import com.ltz.flyfun.parser.bean.DetailsDataBean;
import com.ltz.flyfun.utils.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;

/**

 * @date 2024/2/17 20:33
 */
public class UpdateImgModel extends BaseModel {
    public void getImg(String oldImgUrl, String descUrl, UpdateImgCallback.DataCallback callback) {
        String url = getHttpUrl(descUrl);
        if (parserInterface.getPostMethodClassName().contains(this.getClass().getName())) {

        } else {
            OkHttpUtils.getInstance().doGet(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure(e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String source = getBody(response);
                        DetailsDataBean detailsDataBean = parserInterface.parserDetails(url, source);
                        if (Utils.isNullOrEmpty(detailsDataBean))
                            callback.errorImg();
                        else
                            callback.successImg(oldImgUrl, detailsDataBean.getImg());
                    } catch (Exception e) {
                        e.printStackTrace();
                        callback.onFailure(e.getMessage());
                    }
                }
            });
        }
    }
}

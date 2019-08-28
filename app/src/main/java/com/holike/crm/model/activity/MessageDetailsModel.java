package com.holike.crm.model.activity;

import com.holike.crm.base.BaseModel;
import com.holike.crm.bean.MessageDetailsBean;
import com.holike.crm.http.MyHttpClient;
import com.holike.crm.http.RequestCallBack;
import com.holike.crm.http.UrlPath;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wqj on 2018/2/25.
 * 消息详情
 */

public class MessageDetailsModel implements BaseModel {

    /**
     * 获取消息详情
     *
     * @param messageId
     * @param listener
     */
    public void getMessageDetails(String messageId, final GetMessageDetailsListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("messageId", messageId);
        MyHttpClient.postByCliId(UrlPath.URL_GET_MESSAGE_INFO, null, params, new RequestCallBack<MessageDetailsBean>() {
            @Override
            public void onFailed(String result) {
                listener.failed(result);
            }

            @Override
            public void onSuccess(MessageDetailsBean result) {
                listener.success(result);
            }
        });
    }

    public interface GetMessageDetailsListener {
        void success(MessageDetailsBean messageDetailsBean);

        void failed(String failed);
    }
}

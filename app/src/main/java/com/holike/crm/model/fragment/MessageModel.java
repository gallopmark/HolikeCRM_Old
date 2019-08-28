package com.holike.crm.model.fragment;

import com.holike.crm.base.BaseModel;
import com.holike.crm.bean.MessageBean;
import com.holike.crm.http.MyHttpClient;
import com.holike.crm.http.RequestCallBack;
import com.holike.crm.http.UrlPath;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wqj on 2018/2/24.
 * 消息
 */

public class MessageModel implements BaseModel {
    /**
     * 获取数据
     *
     * @param pageNo
     * @param pageSize
     * @param type
     * @param listener
     */
    public void getMessage(String pageNo, String pageSize, String type, final GetMessageListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        params.put("type", type);
        MyHttpClient.postByCliId(UrlPath.URL_GET_MESSAGE_LIST, null, params, new RequestCallBack<MessageBean>() {
            @Override
            public void onFailed(String result) {
                listener.failed(result);
            }

            @Override
            public void onSuccess(MessageBean result) {
                listener.success(result);
            }
        });
    }

    public interface GetMessageListener {
        void success(MessageBean messageBean);

        void failed(String failed);
    }
}

package com.holike.crm.model.activity;

import android.content.Context;

import com.holike.crm.base.BaseModel;
import com.holike.crm.helper.UploadImgHelper;
import com.holike.crm.http.MyHttpClient;
import com.holike.crm.http.RequestCallBack;
import com.holike.crm.http.UrlPath;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wqj on 2018/7/16.
 * 售后体验反馈
 */

public class FeedbackModel implements BaseModel {
    private UploadImgHelper uploadImgHelper;

    /**
     * 取消
     */
    public void cancel() {
        if (uploadImgHelper != null) {
            uploadImgHelper.cancel();
        }
    }

    /**
     * 上传图片
     *
     * @param filePaths
     * @param uploadListener
     */
    public void uploadImg(Context context, List<List<String>> filePaths, UploadImgHelper.UploadListener uploadListener) {
        if (uploadImgHelper == null) {
            uploadImgHelper = new UploadImgHelper();
        }
        uploadImgHelper.uploadImg(context, filePaths, uploadListener);
    }

    /**
     * 保存
     *
     * @param orderNo
     * @param questionId
     * @param itemId
     * @param questionDescribe
     * @param questionImg
     * @param expenseId
     * @param solveDescribe
     * @param solveImg
     * @param listener
     */
    public void save(String orderNo, String questionId, String itemId, String questionDescribe, String questionImg, String expenseId, String solveDescribe, String solveImg, final SaveListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("expenseId", expenseId);
        params.put("itemId", itemId);
        params.put("orderNo", orderNo);
        params.put("questionDescribe", questionDescribe);
        params.put("questionId", questionId);
        params.put("questionImg", questionImg);
        params.put("solveDescribe", solveDescribe);
        params.put("solveImg", solveImg);

        MyHttpClient.postByCliId(UrlPath.URL_FEEDBACK, null, params, new RequestCallBack<String>() {
            @Override
            public void onFailed(String result) {
                listener.failed(result);
            }

            @Override
            public void onSuccess(String result) {
                listener.success(result);
            }

        });
    }

    public interface SaveListener {
        void failed(String failed);

        void success(String success);
    }
}

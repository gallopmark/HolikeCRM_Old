package com.holike.crm.model.fragment;

import android.text.TextUtils;

import com.holike.crm.base.BaseModel;
import com.holike.crm.bean.CollectionBean;
import com.holike.crm.bean.CustomerDetailBean;
import com.holike.crm.bean.DivideGuideBean;
import com.holike.crm.http.MyHttpClient;
import com.holike.crm.http.RequestCallBack;
import com.holike.crm.http.UrlPath;
import com.holike.crm.util.Constants;
import com.holike.crm.util.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wqj on 2018/8/2.
 * 所有流程model
 */

public class WorkflowModel implements BaseModel {
    /**
     * 获取分配导购
     *
     * @param houseId
     * @param listener
     */
    public void getDivideGuide(String houseId, final GetDivideGuideListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("houseId", houseId);
        Map<String, String> header = new HashMap<>();
        header.put(Constants.USER_ID, SharedPreferencesUtils.getString(Constants.USER_ID));
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE));
        MyHttpClient.post(UrlPath.URL_GET_DIVIDE_GUIDE, header, params, new RequestCallBack<DivideGuideBean>() {
            @Override
            public void onFailed(String result) {
                listener.failed(result);
            }

            @Override
            public void onSuccess(DivideGuideBean result) {
                listener.success(result);
            }
        });
    }

    public interface GetDivideGuideListener {
        void success(DivideGuideBean bean);

        void failed(String failed);
    }

    /**
     * 获取分配设计师
     *
     * @param houseId
     * @param listener
     */
    public void getDivideDesigner(String houseId, final GetDivideDesignerListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("houseId", houseId);
        Map<String, String> header = new HashMap<>();
        header.put(Constants.USER_ID, SharedPreferencesUtils.getString(Constants.USER_ID));
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE));
        MyHttpClient.post(UrlPath.URL_GET_DIVIDE_DESIGER, header, params, new RequestCallBack<DivideGuideBean>() {
            @Override
            public void onFailed(String result) {
                listener.failed(result);
            }

            @Override
            public void onSuccess(DivideGuideBean result) {
                listener.success(result);
            }
        });
    }

    public interface GetDivideDesignerListener {
        void success(DivideGuideBean bean);

        void failed(String failed);
    }

    /**
     * 获取收款记录
     *
     * @param houseId
     * @param personalId
     * @param listener
     */
    public void getCollection(String houseId, String personalId, final GetCollectionListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("houseId", houseId);
        params.put("personalId", personalId);
        Map<String, String> header = new HashMap<>();
        header.put(Constants.USER_ID, SharedPreferencesUtils.getString(Constants.USER_ID, ""));
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE));
        MyHttpClient.post(UrlPath.URL_GET_COLLECTION, header, params, new RequestCallBack<CollectionBean>() {
            @Override
            public void onFailed(String result) {
                listener.failed(result);
            }

            @Override
            public void onSuccess(CollectionBean result) {
                listener.success(result);
            }
        });
    }

    public interface GetCollectionListener {
        void success(CollectionBean collectionBean);

        void failed(String failed);
    }

    public interface WorkflowListener {
        void success(String success);

        void failed(String failed);

    }

    /**
     * 分配导购
     *
     * @param customerStatus
     * @param houseId
     * @param operateCode
     * @param personalId
     * @param prepositionRuleStatus
     * @param type
     * @param listener
     */
    public void divideGuide(String content, String customerStatus, String houseId, String id, String operateCode, String personalId, String prepositionRuleStatus, String type, final WorkflowListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("content", content);
        params.put("customerStatus", customerStatus);
        params.put("houseId", houseId);
        params.put("id", id);
        params.put("operateCode", operateCode);
        params.put("personalId", personalId);
        params.put("prepositionRuleStatus", prepositionRuleStatus);
        params.put("type", type);
        Map<String, String> header = new HashMap<>();
        header.put(Constants.USER_ID, SharedPreferencesUtils.getString(Constants.USER_ID, ""));
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE));
        MyHttpClient.post(UrlPath.URL_WORK_FLOW, header, params, new RequestCallBack<String>() {
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

    /**
     * 分配设计师
     *
     * @param customerStatus
     * @param houseId
     * @param operateCode
     * @param personalId
     * @param prepositionRuleStatus
     * @param type
     * @param listener
     */
    public void divideDesigner(String customerStatus, String houseId, String operateCode, String personalId, String prepositionRuleStatus, String type, final WorkflowListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("customerStatus", customerStatus);
        params.put("houseId", houseId);
        params.put("operateCode", operateCode);
        params.put("personalId", personalId);
        params.put("prepositionRuleStatus", prepositionRuleStatus);
        params.put("type", type);
        Map<String, String> header = new HashMap<>();
        header.put(Constants.USER_ID, SharedPreferencesUtils.getString(Constants.USER_ID, ""));
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE));
        MyHttpClient.post(UrlPath.URL_WORK_FLOW, header, params, new RequestCallBack<String>() {
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

    /**
     * 添加沟通记录
     *
     * @param content
     * @param customerStatus
     * @param houseId
     * @param nextFollowUpDate
     * @param operateCode
     * @param personalId
     * @param prepositionRuleStatus
     * @param type
     * @param listener
     */
    public void addRecord(String content, String customerStatus, String houseId, String nextFollowUpDate, String operateCode, String personalId, String prepositionRuleStatus, String type, final WorkflowListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("content", content);
        params.put("customerStatus", customerStatus);
        params.put("houseId", houseId);
        params.put("nextFollowUpDate", nextFollowUpDate);
        params.put("operateCode", operateCode);
        params.put("personalId", personalId);
        params.put("prepositionRuleStatus", prepositionRuleStatus);
        params.put("type", type);
        Map<String, String> header = new HashMap<>();
        header.put(Constants.USER_ID, SharedPreferencesUtils.getString(Constants.USER_ID, ""));
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE));
        MyHttpClient.post(UrlPath.URL_WORK_FLOW, header, params, new RequestCallBack<String>() {
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

    /**
     * 预约量房
     *
     * @param content
     * @param customerStatus
     * @param houseId
     * @param id
     * @param nextFollowUpDate
     * @param operateCode
     * @param personalId
     * @param prepositionRuleStatus
     * @param listener
     */
    public void bookingMeasure(String content, String customerStatus, String houseId, String id, String nextFollowUpDate, String operateCode, String personalId, String prepositionRuleStatus, String type, final WorkflowListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("content", content);
        params.put("customerStatus", customerStatus);
        params.put("houseId", houseId);
        params.put("id", id);
        params.put("nextFollowUpDate", nextFollowUpDate);
        params.put("operateCode", operateCode);
        params.put("personalId", personalId);
        params.put("prepositionRuleStatus", prepositionRuleStatus);
        params.put("type", type);
        Map<String, String> header = new HashMap<>();
        header.put(Constants.USER_ID, SharedPreferencesUtils.getString(Constants.USER_ID, ""));
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE));
        MyHttpClient.post(UrlPath.URL_WORK_FLOW, header, params, new RequestCallBack<String>() {
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

    /**
     * 上传量房结果
     *
     * @param content
     * @param customerStatus
     * @param date
     * @param houseId
     * @param id
     * @param nextFollowUpDate
     * @param operateCode
     * @param personalId
     * @param prepositionRuleStatus
     * @param listener
     */
    public void uploadMeasureResult(String content, String customerStatus, String date, String houseId, String id, String nextFollowUpDate, String operateCode, String personalId, String prepositionRuleStatus, final WorkflowListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("content", content);
        params.put("customerStatus", customerStatus);
        params.put("date", date);
        params.put("houseId", houseId);
        params.put("id", id);
        params.put("nextFollowUpDate", nextFollowUpDate);
        params.put("operateCode", operateCode);
        params.put("personalId", personalId);
        params.put("prepositionRuleStatus", prepositionRuleStatus);
        Map<String, String> header = new HashMap<>();
        header.put(Constants.USER_ID, SharedPreferencesUtils.getString(Constants.USER_ID, ""));
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE));
        MyHttpClient.post(UrlPath.URL_WORK_FLOW, header, params, new RequestCallBack<String>() {
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

    /**
     * 收取订金
     *
     * @param content
     * @param customerStatus
     * @param houseId
     * @param id
     * @param operateCode
     * @param personalId
     * @param prepositionRuleStatus
     * @param type
     * @param listener
     */
    public void collectDeposit(String content, String customerStatus, String houseId, String id,
                               String nextFollowUpDate, String operateCode, String personalId,
                               String prepositionRuleStatus, String type, String enhouseType, final WorkflowListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("content", content);
        params.put("customerStatus", customerStatus);
        params.put("houseId", houseId);
        params.put("id", id);
        params.put("nextFollowUpDate", nextFollowUpDate);
        params.put("operateCode", operateCode);
        params.put("personalId", personalId);
        params.put("prepositionRuleStatus", prepositionRuleStatus);
        params.put("type", type);
        params.put("enhouseType", enhouseType);
        Map<String, String> header = new HashMap<>();
        header.put(Constants.USER_ID, SharedPreferencesUtils.getString(Constants.USER_ID, ""));
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE));
        MyHttpClient.post(UrlPath.URL_WORK_FLOW, header, params, new RequestCallBack<String>() {
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

    /**
     * 店长查房
     *
     * @param content
     * @param customerStatus
     * @param houseId
     * @param operateCode
     * @param pass
     * @param personalId
     * @param prepositionRuleStatus
     * @param listener
     */
    public void shoperCheck(String content, String customerStatus, String houseId, String operateCode, String pass, String personalId, String prepositionRuleStatus, final WorkflowListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("content", content);
        params.put("customerStatus", customerStatus);
        params.put("houseId", houseId);
        params.put("operateCode", operateCode);
        params.put("pass", pass);
        params.put("personalId", personalId);
        params.put("prepositionRuleStatus", prepositionRuleStatus);
        Map<String, String> header = new HashMap<>();
        header.put(Constants.USER_ID, SharedPreferencesUtils.getString(Constants.USER_ID, ""));
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE));
        MyHttpClient.post(UrlPath.URL_WORK_FLOW, header, params, new RequestCallBack<String>() {
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

    /**
     * 上传方案
     *
     * @param content
     * @param customerStatus
     * @param date
     * @param houseId
     * @param id
     * @param nextFollowUpDate
     * @param operateCode
     * @param personalId
     * @param prepositionRuleStatus
     * @param listener
     */
    public void uploadPlan(String content, String customerStatus, String date, String houseId, String id, String nextFollowUpDate, String operateCode, String personalId, String prepositionRuleStatus, final WorkflowListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("content", content);
        params.put("customerStatus", customerStatus);
        params.put("date", date);
        params.put("houseId", houseId);
        params.put("id", id);
        params.put("nextFollowUpDate", nextFollowUpDate);
        params.put("operateCode", operateCode);
        params.put("personalId", personalId);
        params.put("prepositionRuleStatus", prepositionRuleStatus);
        Map<String, String> header = new HashMap<>();
        header.put(Constants.USER_ID, SharedPreferencesUtils.getString(Constants.USER_ID, ""));
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE));
        MyHttpClient.post(UrlPath.URL_WORK_FLOW, header, params, new RequestCallBack<String>() {
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

    /**
     * 签约
     *
     * @param content
     * @param customerStatus
     * @param houseId
     * @param id
     * @param nextFollowUpDate
     * @param operateCode
     * @param personalId
     * @param prepositionRuleStatus
     * @param type
     * @param listener
     */
    public void signed(String content, String customerStatus, String houseId, String id, String nextFollowUpDate, String operateCode, String pass, String personalId, String prepositionRuleStatus, String type, String depositAmount, String lastMoney, final WorkflowListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("content", content);
        params.put("customerStatus", customerStatus);
        params.put("houseId", houseId);
        params.put("id", id);
        params.put("nextFollowUpDate", nextFollowUpDate);
        params.put("operateCode", operateCode);
        params.put("pass", pass);
        params.put("personalId", personalId);
        params.put("prepositionRuleStatus", prepositionRuleStatus);
        params.put("type", type);
        params.put("depositAmount", depositAmount);
        params.put("lastMoney", lastMoney);
        Map<String, String> header = new HashMap<>();
        header.put(Constants.USER_ID, SharedPreferencesUtils.getString(Constants.USER_ID, ""));
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE));
        MyHttpClient.post(UrlPath.URL_WORK_FLOW, header, params, new RequestCallBack<String>() {
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

    /**
     * 上传复尺结果
     *
     * @param content
     * @param customerStatus
     * @param houseId
     * @param operateCode
     * @param pass
     * @param personalId
     * @param prepositionRuleStatus
     * @param listener
     */
    public void upLoadRemeasure(String id, String content, String customerStatus, String houseId, String nextFollowUpDate, String operateCode, String pass, String personalId, String prepositionRuleStatus, final WorkflowListener listener) {
        Map<String, String> params = new HashMap<>();

        params.put("id", id);
        params.put("content", content);
        params.put("customerStatus", customerStatus);
        params.put("houseId", houseId);
        params.put("nextFollowUpDate", nextFollowUpDate);
        params.put("operateCode", operateCode);
        params.put("pass", pass);
        params.put("personalId", personalId);
        params.put("prepositionRuleStatus", prepositionRuleStatus);
        Map<String, String> header = new HashMap<>();
        header.put(Constants.USER_ID, SharedPreferencesUtils.getString(Constants.USER_ID, ""));
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE));
        MyHttpClient.post(UrlPath.URL_WORK_FLOW, header, params, new RequestCallBack<String>() {
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

    /**
     * 预约安装
     *
     * @param content
     * @param customerStatus
     * @param houseId
     * @param id
     * @param nextFollowUpDate
     * @param operateCode
     * @param personalId
     * @param prepositionRuleStatus
     * @param type
     * @param listener
     */
    public void bookingInstall(String content, String customerStatus, String houseId, String id, String nextFollowUpDate, String operateCode, String personalId, String prepositionRuleStatus, String type, final WorkflowListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("content", content);
        params.put("customerStatus", customerStatus);
        params.put("houseId", houseId);
        params.put("id", id);
        params.put("nextFollowUpDate", nextFollowUpDate);
        params.put("operateCode", operateCode);
        params.put("personalId", personalId);
        params.put("prepositionRuleStatus", prepositionRuleStatus);
        params.put("type", type);
        Map<String, String> header = new HashMap<>();
        header.put(Constants.USER_ID, SharedPreferencesUtils.getString(Constants.USER_ID, ""));
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE));
        MyHttpClient.post(UrlPath.URL_WORK_FLOW, header, params, new RequestCallBack<String>() {
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

    /**
     * 安装完成
     *
     * @param content
     * @param customerStatus
     * @param houseId
     * @param id
     * @param nextFollowUpDate
     * @param operateCode
     * @param prepositionRuleStatus
     * @param listener
     */
    public void installed(String content, String customerStatus, String houseId, String id, String nextFollowUpDate, String operateCode, String personalId, String prepositionRuleStatus, final WorkflowListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("content", content);
        params.put("customerStatus", customerStatus);
        params.put("houseId", houseId);
        params.put("id", id);
        params.put("nextFollowUpDate", nextFollowUpDate);
        params.put("operateCode", operateCode);
        params.put("personalId", personalId);
        params.put("prepositionRuleStatus", prepositionRuleStatus);
        Map<String, String> header = new HashMap<>();
        header.put(Constants.USER_ID, SharedPreferencesUtils.getString(Constants.USER_ID, ""));
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE));
        MyHttpClient.post(UrlPath.URL_WORK_FLOW, header, params, new RequestCallBack<String>() {
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

    /**
     * 收尾款
     *
     * @param content
     * @param customerStatus
     * @param houseId
     * @param operateCode
     * @param personalId
     * @param prepositionRuleStatus
     * @param type
     * @param listener
     */
    public void collectMoney(String content, String customerStatus, String houseId, String id, String lastMoney, String operateCode, String personalId, String prepositionRuleStatus, String type, final WorkflowListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("content", content);
        params.put("customerStatus", customerStatus);
        params.put("houseId", houseId);
        params.put("id", id);
        params.put("lastMoney", lastMoney);
        params.put("operateCode", operateCode);
        params.put("personalId", personalId);
        params.put("prepositionRuleStatus", prepositionRuleStatus);
        params.put("type", type);
        Map<String, String> header = new HashMap<>();
        header.put(Constants.USER_ID, SharedPreferencesUtils.getString(Constants.USER_ID, ""));
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE));
        MyHttpClient.post(UrlPath.URL_WORK_FLOW, header, params, new RequestCallBack<String>() {
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

    /**
     * 已流失
     *
     * @param content
     * @param customerStatus
     * @param houseId
     * @param id
     * @param operateCode
     * @param personalId
     * @param prepositionRuleStatus
     * @param type
     * @param listener
     */
    public void lossed(String content, String customerStatus, String houseId, String id, String operateCode, String personalId, String prepositionRuleStatus, String type, final WorkflowListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("content", content);
        params.put("customerStatus", customerStatus);
        params.put("houseId", houseId);
        params.put("id", id);
        params.put("operateCode", operateCode);
        params.put("personalId", personalId);
        params.put("prepositionRuleStatus", prepositionRuleStatus);
        params.put("type", type);
        Map<String, String> header = new HashMap<>();
        header.put(Constants.USER_ID, SharedPreferencesUtils.getString(Constants.USER_ID, ""));
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE));
        MyHttpClient.post(UrlPath.URL_WORK_FLOW, header, params, new RequestCallBack<String>() {
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

    /**
     * 编辑客户
     *
     * @param personalBean
     * @param listener
     */
    public void editCustimer(CustomerDetailBean.PersonalBean personalBean, final WorkflowListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("associates", personalBean.getAssociates());
        params.put("gender", personalBean.getGender());
        params.put("personalId", personalBean.getPersonalId());
        params.put("phoneNumber", personalBean.getPhoneNumber());
        params.put("shopId", personalBean.getShopId());
        params.put("source", personalBean.getSource());
        params.put("specialCustomers", personalBean.getSpecialCustomers());
        params.put("userName", personalBean.getUserName());
        Map<String, String> header = new HashMap<>();
        header.put(Constants.USER_ID, SharedPreferencesUtils.getString(Constants.USER_ID, ""));
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE));
        MyHttpClient.get(UrlPath.URL_EDIT_CUSTOMER, header, params, new RequestCallBack<CustomerDetailBean.PersonalBean>() {
            @Override
            public void onFailed(String result) {
                listener.failed(result);
            }

            @Override
            public void onSuccess(CustomerDetailBean.PersonalBean result) {
                listener.success(result.toString());
            }
        });
    }

    /**
     * 编辑房屋
     *
     * @param houseInfoBean
     * @param name
     * @param number
     * @param personalId
     * @param listener
     */
    public void editHouse(CustomerDetailBean.CustomerDetailInfoListBean.ListHouseInfoBean houseInfoBean, String name, String number, String personalId, final WorkflowListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("area", houseInfoBean.getArea());
        params.put("budget", houseInfoBean.getBudget());
        params.put("buildingName", houseInfoBean.getBuildingName());
        params.put("buildingNumber", houseInfoBean.getBuildingNumber());
        params.put("decorateType", houseInfoBean.getDecorateType());
        params.put("favColorCode", houseInfoBean.getFavColorCode());
        params.put("favTextureCode", houseInfoBean.getFavTextureCode());
        params.put("decorateProperties", houseInfoBean.getDecorateProperties());
        if (!TextUtils.isEmpty(houseInfoBean.getHouseId())) {
            params.put("houseId", houseInfoBean.getHouseId());
        }
        params.put("houseType", houseInfoBean.getHouseType());
        params.put("remark", houseInfoBean.getRemark());
        params.put("number", number);
        params.put("name", name);
        params.put("personalId", personalId);
        params.put("checkbulidingCode", houseInfoBean.getCheckbulidingCode());
        params.put("customizeTheSpace", houseInfoBean.getCustomizeTheSpace());
        params.put("decorationProgress", houseInfoBean.getDecorationProgress());
        params.put("furnitureDemand", houseInfoBean.getFurnitureDemand());
        params.put("plannedBaseDecorateDate", houseInfoBean.getPlannedBaseDecorateDate());
        params.put("plannedHouseDeliveryDate", houseInfoBean.getPlannedHouseDeliveryDate());
        params.put("salesId", houseInfoBean.getSalesId());
        params.put("shopId", houseInfoBean.getShopId());

        Map<String, String> header = new HashMap<>();
        header.put(Constants.USER_ID, SharedPreferencesUtils.getString(Constants.USER_ID, ""));
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE));
        MyHttpClient.post(UrlPath.URL_EDIT_HOUSE, header, params, new RequestCallBack<String>() {
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

    /**
     * 撤销
     *
     * @param cancelReason
     * @param customerStatus
     * @param houseId
     * @param optCode
     * @param listener
     */
    public void revoke(String cancelReason, String customerStatus, String houseId, String optCode, final WorkflowListener listener) {
        Map<String, String> params = new HashMap<>();
        params.put("cancelReason", cancelReason);
        params.put("customerStatus", customerStatus);
        params.put("houseId", houseId);
        params.put("optCode", optCode);
        Map<String, String> header = new HashMap<>();
        header.put(Constants.USER_ID, SharedPreferencesUtils.getString(Constants.USER_ID, ""));
        header.put(Constants.COOKIE, SharedPreferencesUtils.getString(Constants.COOKIE));
        MyHttpClient.post(UrlPath.URL_CANCEL, header, params, new RequestCallBack<String>() {
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
}

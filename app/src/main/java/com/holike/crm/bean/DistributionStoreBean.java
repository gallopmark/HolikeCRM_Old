package com.holike.crm.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by wqj on 2018/9/19.
 */

public class DistributionStoreBean implements Serializable {

    /**
     * shopId : 20700098
     * buildShopOaNumber :
     * businessLicense : X
     * businessLicenseAddress : 居然之家2121
     * centerAreaManager : 0001
     * cityCode : 140300
     * country : CN
     * countyCity :
     * countyCode :
     * createBy : crmadmin
     * createDate : 2018-01-24
     * dealerId : 12100300
     * dealerType : SD07
     * decorationDate : 1502812800000
     * decorationInfo :
     * designerName :
     * designerPhone :
     * designerQq :
     * frequentContactsQq :
     * language : ZH
     * managerName :
     * managerPhone :
     * managerQq :
     * materialType : 00010000,00100000,01000000,10000000
     * oldShopId : 21040
     * openTime : 2011-09-01
     * phone : 0353-4365145
     * postcode :
     * principalEmali : 769573467@qq.com
     * principalPhone : 13934030856
     * provinceCode : 140000
     * regionManager : 01
     * searchTerms : 阳泉经济技术开发区天野装饰材料经销部
     * shopAddress : 山西省阳泉市经济技术开发区滨河东路居然之家2121
     * shopArea : 338.0
     * shopName : 阳泉经济技术开发区天野装饰材料经销部
     * shopPrincipal : 王金祥
     * shopTypeCode : 02
     * shoppingGuideName :
     * shoppingGuidePhone :
     * shoppingGuideQq :
     * shutTime : null
     * statusCode : 02
     * transferDate : null
     * updateBy : 15721
     * updateDate : 2018-08-16
     * virtualUserId : VB201808000032
     * whetherReceive :
     */

    private String shopId;
    private String shopName;

    private int isSelect;
    private String status;

    public DistributionStoreBean(String shopId, String shopName) {
        this.shopId = shopId;
        this.shopName = shopName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getIsSelect() {
        return isSelect;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj instanceof DistributionStoreBean) {
            return shopId.equals(((DistributionStoreBean) obj).shopId) && shopName.equals(((DistributionStoreBean) obj).shopName);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return 31 + (!TextUtils.isEmpty(shopId) ? shopId.hashCode() : 0);
    }
}

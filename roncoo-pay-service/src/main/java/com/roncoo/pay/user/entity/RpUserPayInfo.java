/*
 * Copyright 2015-2102 RonCoo(http://www.roncoo.com) Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.roncoo.pay.user.entity;

import java.io.Serializable;

import com.roncoo.pay.common.core.entity.BaseEntity;

/**
 * 用户第三方支付信息实体类
 * 龙果学院：www.roncoo.com
 * @author：zenghao
 */
public class RpUserPayInfo extends BaseEntity implements Serializable {

	/**
	 * 对应关系
	 * 微信：appid
	 * 支付宝：partner
	 */
    private String appId;

    private String appSectet;

    /**
     * 对应关系
     * 微信：merchantid
     * 支付宝：seller_id
     */
    private String merchantId;

    private String appType;

    private String userNo;

    private String userName;
    
    /**
     * 对应关系
     * 微信：partnerkey
     * 支付宝：key
     */
    private String partnerKey;
    
    private String payWayCode;

    private String payWayName;
    
    /**
     * 支付宝线下产品appid
     */
    private String offlineAppId;
    /**
     * 支付宝私钥
     */
    private String rsaPrivateKey;
    
    /**
     * 支付宝公钥
     */
    private String rsaPublicKey;

    public String getOfflineAppId() {
		return offlineAppId;
	}

	public void setOfflineAppId(String offlineAppId) {
		this.offlineAppId = offlineAppId;
	}

	public String getRsaPrivateKey() {
		return rsaPrivateKey;
	}

	public void setRsaPrivateKey(String rsaPrivateKey) {
		this.rsaPrivateKey = rsaPrivateKey;
	}

	public String getRsaPublicKey() {
		return rsaPublicKey;
	}

	public void setRsaPublicKey(String rsaPublicKey) {
		this.rsaPublicKey = rsaPublicKey;
	}

	public String getPayWayCode() {
		return payWayCode;
	}

	public void setPayWayCode(String payWayCode) {
		this.payWayCode = payWayCode;
	}

	public String getPayWayName() {
		return payWayName;
	}

	public void setPayWayName(String payWayName) {
		this.payWayName = payWayName;
	}

	public String getPartnerKey() {
		return partnerKey;
	}

	public void setPartnerKey(String partnerKey) {
		this.partnerKey = partnerKey;
	}

	private static final long serialVersionUID = 1L;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getAppSectet() {
        return appSectet;
    }

    public void setAppSectet(String appSectet) {
        this.appSectet = appSectet == null ? null : appSectet.trim();
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType == null ? null : appType.trim();
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo == null ? null : userNo.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }
}
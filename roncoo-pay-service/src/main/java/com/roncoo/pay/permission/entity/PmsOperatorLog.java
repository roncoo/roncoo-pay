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
package com.roncoo.pay.permission.entity;

/**
 * 权限管理-操作员操作日志..
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public class PmsOperatorLog extends PermissionBaseEntity {

	private static final long serialVersionUID = 742891253537618199L;

	private Long operatorId; // 操作员ID
	private String operatorName; // 操作员登录名
	private String operateType; // 操作类型（参与枚举:OperatorLogTypeEnum,1:增加,2:修改,3:删除,4:查询,5:登录）
	private String ip; // IP地址
	private String content; // 操作内容

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}

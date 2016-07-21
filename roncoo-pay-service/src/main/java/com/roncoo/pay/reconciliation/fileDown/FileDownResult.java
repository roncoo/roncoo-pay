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
package com.roncoo.pay.reconciliation.fileDown;

import java.io.Serializable;

/**
 * 微信文件下载返回结果实体
 *
 * 龙果学院：www.roncoo.com
 * 
 * @author：shenjialong
 */
public class FileDownResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9154198958690896977L;

	/**
	 * 文件编码
	 */
	private String fileCharSet = "UTF-8";

	/**
	 * 文件内容
	 */
	private String fileContent;

	/**
	 * 文件是否由平台生成
	 */
	private boolean isPlatBuild = true;

	public String getFileCharSet() {
		return fileCharSet;
	}

	public void setFileCharSet(String fileCharSet) {
		this.fileCharSet = fileCharSet;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public boolean isPlatBuild() {
		return isPlatBuild;
	}

	public void setPlatBuild(boolean isPlatBuild) {
		this.isPlatBuild = isPlatBuild;
	}

}

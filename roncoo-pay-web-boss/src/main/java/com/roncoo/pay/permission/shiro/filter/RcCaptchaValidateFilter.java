package com.roncoo.pay.permission.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

public class RcCaptchaValidateFilter extends AccessControlFilter {

	private boolean captchaEbabled = true;// 是否开启验证码支持

	private String captchaParam = "captchaCode";// 前台提交的验证码参数名

	private String failureKeyAttribute = "shiroLoginFailure"; // 验证码验证失败后存储到的属性名

	public void setCaptchaEbabled(boolean captchaEbabled) {
		this.captchaEbabled = captchaEbabled;
	}

	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}

	public void setFailureKeyAttribute(String failureKeyAttribute) {
		this.failureKeyAttribute = failureKeyAttribute;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		// 1、设置验证码是否开启属性，页面可以根据该属性来决定是否显示验证码
		request.setAttribute("captchaEbabled", captchaEbabled);

		HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
		// 2、判断验证码是否禁用 或不是表单提交（允许访问）
		if (captchaEbabled == false || !"post".equalsIgnoreCase(httpServletRequest.getMethod())) {
			return true;
		}
		// 3、此时是表单提交，验证验证码是否正确
		// 获取页面提交的验证码
		String submitCaptcha = httpServletRequest.getParameter(captchaParam);
		// 获取session中的验证码
		String captcha = (String) httpServletRequest.getSession().getAttribute("rcCaptcha");
		if (submitCaptcha.equals(captcha)) {
			return true;
		}
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// 如果验证码失败了，存储失败key属性
		request.setAttribute(failureKeyAttribute, "验证码错误!");
		return true;
	}

}

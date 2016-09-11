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
package com.roncoo.pay.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.roncoo.pay.controller.common.ConstantClass;
import com.roncoo.pay.user.entity.RpUserInfo;


/**
 * 粗粒度权限控制拦截过滤器
 * 龙果学院：www.roncoo.com
 * @author zenghao
 */
public class UserFilter implements Filter {
    
    private static final Log LOG = LogFactory.getLog(UserFilter.class);

    public void destroy() {
        // Do nothing because of X and Y.
    }

    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String uri = request.getServletPath(); // 请求路径
        LOG.info("=== uri=" + uri);
        
        // 获取登录的用户
        RpUserInfo rpUserInfo = (RpUserInfo)request.getSession().getAttribute(ConstantClass.USER);
        // 如果未登录,重定向到登录界面
        if (uri.contains("merchant") && rpUserInfo == null) {
            HttpServletResponse response = (HttpServletResponse) res;
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            chain.doFilter(req, res);
        }
    }

    public void init(FilterConfig arg0) throws ServletException {
        // Do nothing because of X and Y.
    }

}

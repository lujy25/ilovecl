package com.ilovecl.interceptor;

import com.ilovecl._const.StudentConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 学生登录验证的拦截器
 *
 * @author qiuyongchen
 *         email:qiuych3@mail2.sysu.edu.cn
 *         copyRight:The MIT License (MIT)
 *         Copyright (c) 2016 邱永臣
 *         Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *         documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 *         the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 *         to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *         <p>
 *         The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 *         Software.
 *         <p>
 *         THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *         WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *         OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 *         OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * @since 2016-06-01 20:18
 */
public class StudentSessionInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Object email = httpServletRequest.getSession().getAttribute(StudentConst.STUDENT_EMAIL);
        if (email == null) {
            logger.info("用户尚未登录，将其重定向至登录页面");
            httpServletResponse.sendRedirect("/student/login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, ModelAndView mav) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, Exception excptn) throws Exception {
    }

}

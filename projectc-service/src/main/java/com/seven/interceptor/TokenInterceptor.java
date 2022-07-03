package com.seven.interceptor;

import com.seven.utils.TokenUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token拦截器
 */
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //放行预请求
        if ("OPTIONS".equals(request.getMethod().toString())){
            return true;
        }
        String url = request.getRequestURI();
        System.out.println(url);
        if (!url.endsWith("login") || !url.endsWith("register") || !url.endsWith("validate_sid") || !url.endsWith("135799download")){
            //从http请求头中取出token
            String token = request.getHeader("Authorization");
            System.out.println(token);
            //执行认证
            if (TokenUtil.verify(token)){
                return true;
            }else {
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

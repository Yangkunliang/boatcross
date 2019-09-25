package com.kelystor.boatcross.interceptor;

import com.kelystor.boatcross.dao.UserMapper;
import com.kelystor.boatcross.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.getPrincipal() != null) {
            String username = (String) subject.getPrincipal();
            User currentUser = userMapper.findByUsername(username);
            if (currentUser == null) {
                currentUser = new User();
                currentUser.setName("");
                currentUser.setUsername(username);
                userMapper.save(currentUser);
            }
            request.setAttribute("currentUser", currentUser);
        }

        return true;
    }

}
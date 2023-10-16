package com.dbms.project.interceptor;

import com.dbms.project.model.User;
import com.dbms.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@SessionScope
public class DefaultInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && request.getUserPrincipal() != null) {
            String loggedInUserUsername = request.getUserPrincipal().getName();
            User user = userService.getUserByUsername(loggedInUserUsername);
            modelAndView.addObject("principal", user);
        }
    }
}

package com.DigitalWindTeam.Justudy.interceptor;

import com.DigitalWindTeam.Justudy.models.Course;
import com.DigitalWindTeam.Justudy.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class MinimalInterceptor implements HandlerInterceptor {
    private TokenUtils tokenUtils;

    @Autowired
    public MinimalInterceptor(TokenUtils tokenUtils) {
        this.tokenUtils = tokenUtils;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // long startTime = System.currentTimeMillis();

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
//         System.out.println("MINIMAL: INTERCEPTOR POSTHANDLE CALLED");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception
    {
//        System.out.println("MINIMAL: INTERCEPTOR AFTERCOMPLETION CALLED");
    }
}
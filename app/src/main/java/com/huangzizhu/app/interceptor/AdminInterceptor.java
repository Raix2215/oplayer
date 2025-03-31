package com.huangzizhu.interceptor;

import com.huangzizhu.exception.UserNotFoundException;
import com.huangzizhu.mapper.AdminMapper;
import com.huangzizhu.pojo.Admin;
import com.huangzizhu.utils.CurrentHolder;
import com.huangzizhu.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Autowired
    private AdminMapper adminMapper;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();

        // 拦截特定路径
        if (path.contains("/admin")) {
            log.info("拦截路径:{}", path);
            // 如果是登录请求，直接放行
            if (request.getRequestURI().contains("/login")){
                log.info("login, 放行");
                return true;
            }
            // 如果是其他请求，判断是否有token
            String token = request.getHeader("token");
            if (token == null || token.isEmpty()){
                log.info("token为空，拦截");
                response.setStatus(401);
                return false;
            }
            // 如果有token,校验token
            try {
                //获取token中的信息
                Jws<Claims> claims = JWTUtil.parseToken(token);
                Integer id = claims.getPayload().get("id", Integer.class);
                String username = claims.getPayload().get("username", String.class);
                Boolean isAdmin = claims.getPayload().get("isAdmin", Boolean.class);
                if(isAdmin){
                    log.info("id:{},username:{},进入管理员界面，进行校验",id,username);
                    Admin admin = adminMapper.checkAdmin(id,username);
                    if(admin == null){
                        log.warn("拥有管理员令牌却验证失败：id:{},username:{}",id,username);
                        throw new UserNotFoundException("管理员不存在");
                    }
                    // token校验成功，放行
                    log.info("token校验成功，放行");
                    return true;
                }
                else{
                    log.info("id:{},username:{},非管理员，拦截",id,username);
                    response.setStatus(401);
                    return false;
                }
//                CurrentHolder.setCurrentId(id);
//                CurrentHolder.setCurrentName(username);
            }catch (Exception e){
                log.info("token校验失败，拦截");
                response.setStatus(401);
                return false;
            }
        }
        return true; // 继续后续的处理
    }
}
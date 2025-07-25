package com.huangzizhu.app.interceptor;

import com.huangzizhu.utils.CurrentHolder;
import com.huangzizhu.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class UserInterceptor implements HandlerInterceptor {
    private String[] acceptPath = {"/login", "/reg", "/tool","stream"};

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        // 拦截特定路径
            log.info("拦截路径:{}", path);
            // 如果是登录和注册请求，直接放行
            if (isAccessPath(path)){
                log.info("{},放行", path);
                return true;
            }
            // 如果是其他请求，判断是否有token
            String token = request.getHeader("token");
            if (token == null || token.isEmpty()){
                log.info("token为空，拦截");
                response.setStatus(401);
                return false;
            }
            //获取令牌，由aop层进行权限校验
            // 如果有token,校验token
            try {
                //获取token中的信息
                Jws<Claims> claims = JWTUtil.parseToken(token);
                Integer id = claims.getPayload().get("id", Integer.class);
                String username = claims.getPayload().get("username", String.class);
                Boolean isAdmin = claims.getPayload().get("isAdmin", Boolean.class);
                CurrentHolder.setCurrentId(id);
                CurrentHolder.setCurrentName(username);
                CurrentHolder.setCurrentIsAdmin(isAdmin);
                // token校验成功，放行
                log.info("token校验成功，放行");
                return true;
            }catch (Exception e){
                log.info("token校验失败，拦截");
                response.setStatus(401);
                return false;
            }
        }

        private boolean isAccessPath(String path) {
            for (String p : acceptPath) {
                if (path.contains(p)) {
                    return true;
                }
            }
            return false;
        }

}

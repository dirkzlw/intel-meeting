package com.intel.meeting.config;

import com.intel.meeting.interceptor.AdminInterceptor;
import com.intel.meeting.interceptor.ErrorInterceptor;
import com.intel.meeting.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 权限配置类
 *
 * @author Intel-Meeting
 * @create 2019-04-07 10:56
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 自定义拦截器，添加拦截路径和排除拦截路径
     * addPathPatterns():添加需要拦截的路径
     * excludePathPatterns():添加不需要拦截的路径
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //对非注册用户进行拦截
        UserInterceptor userRoot = new UserInterceptor();
        //对游客实现拦截：预定中心、用户认证、联系我们
        registry.addInterceptor(userRoot)
                .addPathPatterns("/to/reserve/center",
                        "/to/user/auth",
                        "/to/user/contact");
        //对管理员进行拦截
        AdminInterceptor adminRoot = new AdminInterceptor();
        //对用户实现拦截：用户管理、控制中心
        registry.addInterceptor(adminRoot)
                .addPathPatterns("/to/usermgn",
                        "/to/control");
        //对跳转错误页面进行拦截
        ErrorInterceptor errorRoot = new ErrorInterceptor();
        registry.addInterceptor(errorRoot);
        super.addInterceptors(registry);

    }
}

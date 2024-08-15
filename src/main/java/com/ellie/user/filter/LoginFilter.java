package com.ellie.user.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import com.ellie.user.model.UserVO;

@Component
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 可以在此進行過濾器初始化操作
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // 檢查 session 中是否有 user 資訊
        UserVO user = (session != null) ? (UserVO) session.getAttribute("user") : null;
        String uri = httpRequest.getRequestURI();

        // 判斷如果是訪問下訂單等需要登入的操作，檢查是否登入
        if (user == null && ( uri.startsWith("/ABC") || uri.startsWith("/abc") || uri.startsWith("/abc")) ) {
            // 如果未登入，重定向到登入頁面
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/user/login");
            return;
        }

        // 如果已登入，繼續處理請求
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 可以在此進行過濾器的清理操作
    }
}



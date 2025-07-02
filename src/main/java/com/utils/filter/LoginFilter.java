package com.utils.filter;

import java.io.IOException;
// 將 javax.servlet.* 改為 jakarta.servlet.*
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import com.entity.UserVO;

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

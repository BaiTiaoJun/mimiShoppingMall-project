package name.baitiaojun.web.interseptor;

import name.baitiaojun.domain.Admin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();
        if ("/admin/login.action".equals(servletPath)) {
            return true;
        } else {
            Admin admin = (Admin) request.getSession().getAttribute("admin");
            if (admin != null) {
                return true;
            } else {
                response.sendRedirect("/login.jsp");
                return false;
            }
        }
    }
}

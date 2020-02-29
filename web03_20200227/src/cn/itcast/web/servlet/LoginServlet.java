package cn.itcast.web.servlet;


import cn.itcast.domain.User;
import cn.itcast.service.UserService;
import cn.itcast.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.判断验证码
        request.setCharacterEncoding("utf-8");
        String verifycode = request.getParameter("verifycode");
        HttpSession session = request.getSession();
        String checkCode = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if(!checkCode.equalsIgnoreCase(verifycode)){
            //验证码错误
            request.setAttribute("loginInfo","验证码错误!");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
            return;
        }
        //2.封装对象并查询
        Map<String, String[]> map = request.getParameterMap();
        User user=new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        UserService service=new UserServiceImpl();
        User loginUser = service.login(user);
        //3.判断是否登陆成功并处理
        if(loginUser!=null){
            session.setAttribute("user",user);
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }else{
            request.setAttribute("loginInfo","用户名或密码错误");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

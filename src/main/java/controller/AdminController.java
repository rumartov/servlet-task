package controller;

import model.User;
import repo.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static model.Role.*;

@WebServlet("adminController")
public class AdminController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        final HttpSession session = req.getSession();

        final AtomicReference<UserDAO> dao = (AtomicReference<UserDAO>) req.getServletContext().getAttribute("dao");

        final String adminName = (String) session.getAttribute("username");
        final String password = (String) session.getAttribute("password");

        session.getAttribute("role");
        if(isAdmin(dao, adminName, password)){
            String role = (String) req.getParameter("role");
            String userName = (String) req.getParameter("username");

            try {
                changeUserRole(userName, role, dao );
            } catch (Exception e) {
                e.printStackTrace();
            }
       }

        res.sendRedirect("admin.jsp");
    }

    private boolean isAdmin(AtomicReference<UserDAO> dao, String userName, String password) {
        return dao.get().getRoleByLoginPassword(userName, password).equals(ADMIN);
    }

    private void changeUserRole(String userName, String role, AtomicReference<UserDAO> dao) throws Exception {
        User u = dao.get().getUserByUsername(userName);

        if(role.equalsIgnoreCase("admin")){
            u.setRole(ADMIN);
        } else if(role.equalsIgnoreCase("user")){
            u.setRole(USER);
        } else {
            u.setRole(GUEST);
        }
    }
}
package security;

import model.Role;
import repo.UserDAO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.nonNull;
import static model.Role.*;

@WebFilter(urlPatterns = {"/"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain)
            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        final String userName = req.getParameter("username");
        final String password = req.getParameter("password");

        @SuppressWarnings("unchecked")
        final AtomicReference<UserDAO> dao = (AtomicReference<UserDAO>) req.getServletContext().getAttribute("dao");

        final HttpSession session = req.getSession();

        if (nonNull(session) &&
                nonNull(session.getAttribute("username")) &&
                nonNull(session.getAttribute("password"))) {

            final Role role = (Role) session.getAttribute("role");

            redirect(req, res, role);

        } else if (dao.get().isExist(userName, password)) {

            final Role role = dao.get().getRoleByLoginPassword(userName, password);

            req.getSession().setAttribute("password", password);
            req.getSession().setAttribute("username", userName);
            req.getSession().setAttribute("role", role);

            redirect(req, res, role);
        } else {
            redirect(req, res, GUEST);
        }

    }

    private void redirect(final HttpServletRequest req,
                          final HttpServletResponse res,
                          final Role role)
            throws ServletException, IOException {

        if (role.equals(ADMIN)) {
            res.sendRedirect("admin.jsp");
        } else if (role.equals(USER)) {
            res.sendRedirect("download.jsp");
        } else {
            req.getRequestDispatcher("login.jsp").forward(req, res);
        }
    }

    @Override
    public void destroy() {
    }

}

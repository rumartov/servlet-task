package repo;

import model.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.atomic.AtomicReference;

import static model.Role.ADMIN;
import static model.Role.USER;

@WebListener
public class ContextListener implements ServletContextListener {

    private AtomicReference<UserDAO> dao;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        dao = new AtomicReference<>(new UserDAO());

        dao.get().add(new User("admin", "qwerty", ADMIN));
        dao.get().add(new User("user1", "123", USER));

        final ServletContext servletContext =
                servletContextEvent.getServletContext();
        servletContext.setAttribute("dao", dao);
        AtomicReference<UserDAO> dao = (AtomicReference<UserDAO>) servletContext.getAttribute("dao");
        dao.get().getAllUsers();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        dao = null;
    }
}
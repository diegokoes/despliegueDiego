package es.daw.jakarta.listeners;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import es.daw.jakarta.models.Carro;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class AppListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    private ServletContext sc;
    DateTimeFormatter df = DateTimeFormatter.ofPattern("hh:mm:ss");

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sc = sce.getServletContext();
        sc.log("******** Inicializando la aplicación!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sc.log("******** Destruyendo la aplicación!");
    }
@Override
public void sessionCreated(HttpSessionEvent se) {
    sc.log("******** inicializando la sesion http");
  
    Carro carro = new Carro();
    se.getSession().setAttribute("carro", carro);
}


    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if ("username".equals(event.getName())) {
            String username = (String) event.getValue();
            if ("admin".equals(username)) {
                LocalDateTime loginTime = LocalDateTime.now();
                event.getSession().setAttribute("loginTime", loginTime);
                sc.log("~~admin se loggeó a las  " + loginTime.format(df));
            }
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if ("username".equals(event.getName())) {
            String username = (String) event.getValue();
            if ("admin".equals(username)) {
                LocalDateTime logoutTime = LocalDateTime.now();
                LocalDateTime loginTime = (LocalDateTime) event.getSession().getAttribute("loginTime");
                Duration duration = Duration.between(loginTime, logoutTime);
                sc.log("~~admin logout a las :  " + logoutTime.format(df));
                sc.log("***** Duración de la sesión: " + duration.toMinutes() + " minutos   *****");
            }
        }
    }

}

package es.daw.jakarta.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import es.daw.jakarta.services.LoginService;
import es.daw.jakarta.services.LoginServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    final static String USERNAME = "admin";
    final static String PASSWORD = "12345";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LoginService auth = new LoginServiceImpl();
        Optional<String> usernameOpt = auth.getUserName(request);

        if (usernameOpt.isPresent()) {
            String username = usernameOpt.get();

            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html lang='es'>");
                out.println("<head>");
                out.println("    <meta charset='UTF-8'>");
                out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("    <title>Login Exitoso</title>");
                out.println("    <!-- Bootstrap CSS -->");
                out.println(
                        "    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css' rel='stylesheet' "
                                +
                                "integrity='sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65' crossorigin='anonymous'>");
                out.println("    <style>");
                out.println("        body {");
                out.println("            background-color: #f8f9fa;");
                out.println("            display: flex;");
                out.println("            justify-content: center;");
                out.println("            align-items: center;");
                out.println("            height: 100vh;");
                out.println("            margin: 0;");
                out.println("        }");
                out.println("        .message-container {");
                out.println("            background-color: #ffffff;");
                out.println("            padding: 2rem;");
                out.println("            border-radius: 0.5rem;");
                out.println("            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);");
                out.println("            text-align: center;");
                out.println("            max-width: 500px;");
                out.println("            width: 100%;");
                out.println("        }");
                out.println("        .btn {");
                out.println("            margin: 0.5rem;");
                out.println("        }");
                out.println("    </style>");
                out.println("</head>");
                out.println("<body>");
                out.println("    <div class='message-container'>");

                out.println("        <h1>Hola " + username + ", has iniciado sesión con éxito!</h1>");
                out.println("        <div class='d-flex justify-content-center'>");
                out.println("            <a href='index.jsp' class='btn btn-primary'>Volver a Inicio</a>");
                out.println("            <a href='logout' class='btn btn-secondary'>Logout</a>");
                out.println("        </div>");
                out.println("    </div>");
                out.println("    <!-- Bootstrap JS -->");
                out.println(
                        "    <script src='https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js' "
                                +
                                "integrity='sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+3q4RYv6jIW3L3Z8q4h+2L6U00Jv6' crossorigin='anonymous'></script>");
                out.println("</body>");
                out.println("</html>");
            }

        } else {
            response.sendRedirect("login.html");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Leer datos del request
        String login = request.getParameter("login");
        String pwd = request.getParameter("pwd");

        response.setContentType("text/html;charset=UTF-8");

        // 2. Procesarlos

        if (USERNAME.equals(login) && PASSWORD.equals(pwd)) { // Validación exitosa
            String colorSeleccionado = request.getParameter("color");
            HttpSession session = request.getSession();
            session.setAttribute("username", login);
            if (colorSeleccionado != null) {
                Cookie color = new Cookie("color", colorSeleccionado);
                response.addCookie(color);
            }

            doGet(request, response);

        } else {

            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html lang='es'>");
                out.println("<head>");
                out.println("    <meta charset='UTF-8'>");
                out.println("    <meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("    <title>Login Fallido</title>");
                out.println("    <!-- Bootstrap CSS -->");
                out.println(
                        "    <link href='https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css' rel='stylesheet' "
                                +
                                "integrity='sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65' crossorigin='anonymous'>");
                out.println("    <style>");
                out.println("        body {");
                out.println("            background-color: #f8f9fa;");
                out.println("            display: flex;");
                out.println("            justify-content: center;");
                out.println("            align-items: center;");
                out.println("            height: 100vh;");
                out.println("            margin: 0;");
                out.println("        }");
                out.println("        .error-container {");
                out.println("            background-color: #ffffff;");
                out.println("            padding: 2rem;");
                out.println("            border-radius: 0.5rem;");
                out.println("            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);");
                out.println("            text-align: center;");
                out.println("            max-width: 500px;");
                out.println("            width: 100%;");
                out.println("        }");
                out.println("        .btn {");
                out.println("            margin-top: 1rem;");
                out.println("        }");
                out.println("    </style>");
                out.println("</head>");
                out.println("<body>");
                out.println("    <div class='error-container'>");
                out.println("        <h1 class='text-danger'>¡Error de Autenticación!</h1>");
                out.println("        <p>El nombre de usuario o la contraseña son incorrectos.</p>");
                out.println("        <a href='login.html' class='btn btn-primary'>Volver al Login</a>");
                out.println("    </div>");
                out.println("    <!-- Bootstrap JS -->");
                out.println(
                        "    <script src='https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js' "
                                +
                                "integrity='sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+3q4RYv6jIW3L3Z8q4h+2L6U00Jv6' crossorigin='anonymous'></script>");
                out.println("</body>");
                out.println("</html>");
            }
        }

    }
}

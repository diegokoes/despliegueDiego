<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Index.jsp</title>
    <!-- Bootstrap CSS -->
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
      integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
      crossorigin="anonymous"
    />
    <style>
      body {
          <%
          String colorFondo = "white"; 
          Cookie[] cookies = request.getCookies();
          if (cookies != null) {
              for (Cookie cookie : cookies) {
                  if ("color".equals(cookie.getName())) {
                      colorFondo = cookie.getValue();
                      break;
                  }
              }
          }
      %>

          background-color: <%= colorFondo %>;          
          display: flex;
          justify-content: center;
          align-items: center;
          height: 100vh; /* Full viewport height */
          margin: 0;
      }
      .nav-container {
          background-color: #ffffff; /* White background for the container */
          padding: 2rem;
          border-radius: 0.5rem;
          box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* Subtle shadow */
          width: 100%;
          max-width: 400px; /* Maximum width for larger screens */
          text-align: center;
      }
      .nav-container h1 {
          margin-bottom: 1.5rem;
          font-size: 1.75rem;
          color: #343a40; /* Dark gray text */
      }
      .list-group-item {
          border: none;
          border-bottom: 1px solid #dee2e6;
      }
      .list-group-item:last-child {
          border-bottom: none;
      }
      .list-group-item a {
          text-decoration: none;
          color: #0d6efd; /* Bootstrap primary color */
          display: block;
          width: 100%;
      }
      .list-group-item a:hover {
          background-color: #f1f1f1;
          border-radius: 0.25rem;
      }
    </style>
  </head>
  <body>
    <% String username = (String) session.getAttribute("username"); %>
    <div class="nav-container">
      <h1>Bienvenid@, <%= (username != null ? username : "invitado") %></h1>
      <ul class="list-group">
        <li class="list-group-item">
          <a href="productos">Mostrar productos HTML</a>
        </li>
        <% if(username==null) {%>
        <li class="list-group-item">
          <a href="login">Login</a>
        </li>
        <%}%> <% if(username!=null) {%>

        <li class="list-group-item">
          <a href="logout">Logout</a>
        </li>
        <%}%>
        <li class="list-group-item">
          <a href="carro/ver">Ver Carro</a>
        </li>
      </ul>
    </div>

    <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+3q4RYv6jIW3L3Z8q4h+2L6U00Jv6"
      crossorigin="anonymous"
    ></script>
  </body>
</html>

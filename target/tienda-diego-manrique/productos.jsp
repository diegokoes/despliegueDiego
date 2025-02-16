<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Optional"%>
<%@page import="es.daw.jakarta.models.Producto"%>
<%@page import="es.daw.jakarta.models.Categoria"%>
<%@page import="es.daw.jakarta.services.*"%>
<%@page import="java.util.Set"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listado de productos</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            color: #333;
            margin: 0;
            padding: 20px;
        }
        h1 {
            color: #5a67d8;
        }
        h2 {
            margin-top: 20px;
            color: #2d3748;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: white;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background-color: #5a67d8;
            color: white;
        }
        td {
            background-color: #f8f9fa;
        }
        a {
            text-decoration: none;
            color: #3182ce;
        }
        a:hover {
            text-decoration: underline;
        }
        .welcome-message {
            background-color: #e2e8f0;
            padding: 10px;
            border-radius: 8px;
            display: inline-block;
        }
        .back-link {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 15px;
            background-color: #5a67d8;
            color: white;
            border-radius: 5px;
            text-decoration: none;
            font-weight: bold;
        }
        .back-link:hover {
            background-color: #434190;
        }
        img {
            max-width: 40%;
        }

        .premio-header {
            color: #d53f8c;
            font-size: 2.5em;
            text-align: center;
            margin-top: 20px;
            animation: fadeIn 2s ease-in-out;
        }
        .premio-img {
            display: block;
            margin: 20px auto;
            max-width: 200px;
            border-radius: 50%;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            transition: transform 0.3s ease;
        }
        .premio-img:hover {
            transform: scale(1.1);
        }
        .no-premio-header {
            color: #718096;
            font-size: 2.5em;
            text-align: center;
            margin-top: 20px;
            animation: fadeIn 2s ease-in-out;
        }
        .no-premio-img {
            display: block;
            margin: 20px auto;
            max-width: 200px;
            border-radius: 50%;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }
    </style>
</head>
<body>
    <h1>Listado de productos</h1>
    <h2>
        <%
            LoginService auth = new LoginServiceImpl();
            Optional<String> sessionOpt = auth.getUserName(request);
            if (sessionOpt.isPresent()) {
        %>
            <span class="welcome-message">Hola <%= sessionOpt.get() %>, bienvenid@!!!!!</span></h2>
            <% 
                Boolean tienePremio = (Boolean) request.getAttribute("tienePremio");
                if (tienePremio == null) {
                    out.println("<p>error en el filtro del premio, esta null</p>");
                } else {
                    if (tienePremio) {
            %>
                        <h1 class="premio-header">¡regalo sorpresa!</h1>
                        <img src="images/happycat.jpg" alt="happy cat :3 " class="premio-img">
            <%
                    } else {
            %>
                        <h1 class="no-premio-header">No te ha tocado nada T_T</h1>
                        <img src="images/sadcat.jpg" alt="T_T" class="no-premio-img">
            <%
                    }
                }
       
            } else {
        %>
            <span class="welcome-message">NO REGISTRADO!!!</span>
        <%
            }
        %>
        <% if (sessionOpt.isPresent()) { %>
            <a href="<%= request.getContextPath() %>/productos/form" class="btn btn-primary">Crear Nuevo Producto</a>
        <% } %>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>NOMBRE</th>
                    <th>CATEGORÍA</th>
                    <th>PRECIO</th>
                    <th>AGREGAR AL CARRITO</th>
                    <th>EDITAR</th>
                    <th>ELIMINAR</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Producto> productos = (List<Producto>) request.getAttribute("productos");
                    for (Producto p : productos) {
                %>
                    <tr>
                        <td><%= p.getId() %></td>
                        <td><%= p.getNombre() %></td>
                        <td><%= p.getCategoria().getNombre() %></td>
                        <td><%= p.getPrecio() %></td>
                        <% if (sessionOpt.isPresent()) { %>
                            <td><a href='<%= request.getContextPath() %>/carro/agregar?id=<%= p.getId() %>'>Agregar al carrito</a></td>
                            <td><a href='<%= request.getContextPath() %>/productos/form?id=<%= p.getId() %>'>Editar</a></td>
                            <td><a href='<%= request.getContextPath() %>/productos/eliminar?id=<%= p.getId() %>'
                                   onclick="return confirm('¿seguro que quieres eliminar este producto?');">Eliminar</a></td>
                        <% } %>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    <a href="index.jsp" class="back-link">Volver a Inicio</a>
</body>
</html>

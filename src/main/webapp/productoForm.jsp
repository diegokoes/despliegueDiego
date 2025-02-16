<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="es.daw.jakarta.models.Producto"%>
<%@page import="es.daw.jakarta.models.Categoria"%>
<%@page import="java.time.LocalDate"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
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
        form {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            max-width: 600px;
            margin: auto;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
        }
        input[type="text"],
        input[type="number"],
        select {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .error-message {
            color: red;
            margin-top: 10px;
        }
        .button-group {
            margin-top: 20px;
            text-align: right;
        }
        .button-group button,
        .button-group a {
            padding: 10px 15px;
            margin-left: 10px;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            font-weight: bold;
            cursor: pointer;
        }
        .button-group button {
            background-color: #5a67d8;
            color: white;
        }
        .button-group a {
            background-color: #e2e8f0;
            color: #333;
        }
        .button-group button:hover {
            background-color: #434190;
        }
        .button-group a:hover {
            background-color: #cbd5e0;
        }
    </style></head>
<body>
    <h1><% out.print(request.getParameter("id") != null ? "Editar Producto" : "Crear Producto"); %></h1>

    <%
        Producto producto = (Producto) request.getAttribute("producto");
        Set<Categoria> categorias = (Set<Categoria>) request.getAttribute("categorias");
        Map<String, String> errores = (Map<String, String>) request.getAttribute("errores");
        if (errores == null) {
            errores = new HashMap<>();
        }
        boolean editando = (producto != null && producto.getId() != null);
    %>

    <form action="<%= request.getContextPath() %>/productos/form" method="post">
        <% if (editando) { %>
            <input type="hidden" name="id" value="<%= producto.getId() %>">
        <% } %>

        <label for="nombre">Nombre:</label>
        <input type="text" id="nombre" name="nombre" value="<%= producto != null ? producto.getNombre() : "" %>">
        <% if (errores.containsKey("nombre")) { %>
            <p class="error-message"><%= errores.get("nombre") %></p>
        <% } %>

        <label for="precio">Precio:</label>
        <input type="number" id="precio" name="precio" value="<%= producto != null ? producto.getPrecio() : "" %>">
        <% if (errores.containsKey("precio")) { %>
            <p class="error-message"><%= errores.get("precio") %></p>
        <% } %>

        <label for="sku">SKU:</label>
        <input type="text" id="sku" name="sku" value="<%= producto != null ? producto.getSku() : "" %>">
        <% if (errores.containsKey("sku")) { %>
            <p class="error-message"><%= errores.get("sku") %></p>
        <% } %>

        <label for="categoriaId">Categoría:</label>
        <select id="categoriaId" name="categoriaId">
            <option value="">Seleccione una categoría</option>
            <% for (Categoria c : categorias) { %>
                <option value="<%= c.getId() %>" <%= (producto != null && producto.getCategoria() != null && producto.getCategoria().getId().equals(c.getId())) ? "selected" : "" %>>
                    <%= c.getNombre() %>
                </option>
            <% } %>
        </select>
        <% if (errores.containsKey("categoriaId")) { %>
            <p class="error-message"><%= errores.get("categoriaId") %></p>
        <% } %>

        <label for="fechaRegistro">Fecha de Registro:</label>
        <input type="date" id="fechaRegistro" name="fechaRegistro" value="<%= producto != null && producto.getFechaRegistro() != null ? producto.getFechaRegistro() : LocalDate.now() %>">
        <% if (errores.containsKey("fechaRegistro")) { %>
            <p class="error-message"><%= errores.get("fechaRegistro") %></p>
        <% } %>
        

        <div class="button-group">
            <button type="submit">Guardar</button>
            <a href="<%= request.getContextPath() %>/productos">Cancelar</a>
        </div>
    </form>
</body>
</html>

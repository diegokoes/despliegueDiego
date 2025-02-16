<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@page import="es.daw.jakarta.models.*"%>
<!DOCTYPE html>
<html lang="es">
  <head>
    <meta charset="UTF-8" />
    <title>Carro de compras</title>
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
      p {
        color: #e53e3e;
      }
      table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
        background-color: white;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
      }
      th,
      td {
        padding: 12px;
        border: 1px solid #ddd;
        text-align: center;
      }
      th {
        background-color: #5a67d8;
        color: white;
      }
      td {
        background-color: #f8f9fa;
      }
      .total-row td {
        font-weight: bold;
        text-align: right;
        background-color: #edf2f7;
      }
      .back-link,
      .button {
        display: inline-block;
        margin-top: 20px;
        padding: 10px 15px;
        background-color: #5a67d8;
        color: white;
        border-radius: 5px;
        text-decoration: none;
        font-weight: bold;
      }
      .back-link:hover,
      .button:hover {
        background-color: #434190;
      }
      .button {
        border: none;
        cursor: pointer;
      }
      .button:disabled {
        background-color: #a0aec0;
        cursor: not-allowed;
      }
      .button-container {
        margin-top: 20px;
      }
    </style>
  </head>
  <body>
    <% String mensaje = (String) request.getAttribute("mensaje"); if (mensaje !=
    null) { %>
    <p style="color: red"><%= mensaje %></p>
    <% } %>

    <h1>Carro de compras</h1>
    <% Carro carro = (Carro) session.getAttribute("carro"); if (carro == null ||
    carro.getItems().isEmpty()) { %>
    <p>Lo sentimos, no hay productos en el carro de compras!</p>
    <% } else { if(carro.aplicarDescuento()){ %>
    <h3>Descuento aplicado del <%=(100 - (carro.getDESCUENTO() * 100))%>%</h3>
    <% } %>
    <form
      action="<%= request.getContextPath() %>/carro/actualizar"
      method="post"
    >
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Precio</th>
            <th>Cantidad</th>
            <th>Total</th>
            <th>Eliminar</th>
          </tr>
        </thead>
        <tbody>
          <% for (ItemCarro item : carro.getItems()) { %>
          <tr>
            <td><%= item.getProducto().getId() %></td>
            <td><%= item.getProducto().getNombre() %></td>
            <td><%= item.getProducto().getPrecio() %></td>
            <td>
              <input
                type="number"
                name="cantidad_<%= item.getProducto().getId() %>"
                value="<%= item.getCantidad() %>"
                min="1"
              />
            </td>
            <td><%= item.getImporte() %></td>
            <td>
              <input
                type="checkbox"
                name="eliminar"
                value="<%= item.getProducto().getId() %>"
              />
            </td>
          </tr>
          <% } %>
          <tr class="total-row">
            <td colspan="4">Total:</td>
            <td colspan="2"><%= carro.getTotal() %></td>
          </tr>
        </tbody>
      </table>

      <div class="button-container">
        <button type="submit" class="button">Actualizar</button>
        <a href="<%= request.getContextPath() %>/productos" class="back-link"
          >Seguir comprando</a
        >
        <a href="<%= request.getContextPath() %>/index.jsp" class="back-link"
          >Volver a Inicio</a
        >
        <a
          href="<%= request.getContextPath() %>/carro/exportar-json"
          class="back-link"
          >Exportar a JSON</a
        >
      </div>
    </form>
    <% } %>
  </body>
</html>

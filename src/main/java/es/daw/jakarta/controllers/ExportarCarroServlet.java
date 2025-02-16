package es.daw.jakarta.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import es.daw.jakarta.models.Carro;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/carro/exportar-json")
public class ExportarCarroServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    HttpSession session = request.getSession();
    Carro carro = (Carro) session.getAttribute("carro");

    if (carro == null || carro.getItems().isEmpty()) {
      request.setAttribute("mensaje", "El carrito está vacío. No se puede exportar a JSON.");
      request.getRequestDispatcher("/carro.jsp").forward(request, response);
      return;
    }

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());

    String carroJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(carro);

    response.setContentType("application/json");
    response.setHeader("Content-Disposition", "attachment;filename=carrito.json");
    response.getWriter().write(carroJson);
  }
}

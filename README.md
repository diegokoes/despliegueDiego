# Tienda Diego Manrique

Esta aplicación es un proyecto web desarrollado con Jakarta EE y Maven, que implementa una tienda de ejemplo. A continuación se detalla la arquitectura y los componentes principales:

## Componentes

### Modelo

- **Entidades y clases de dominio**:  
  Las clases del modelo representan los datos de la aplicación, por ejemplo:  
  - [`es.daw.jakarta.models.Producto`](src/main/java/es/daw/jakarta/models/Producto.java)  
  - [`es.daw.jakarta.models.Carro`](src/main/java/es/daw/jakarta/models/Carro.java)  
  - [`es.daw.jakarta.models.Categoria`](src/main/java/es/daw/jakarta/models/Categoria.java)  
  - [`es.daw.jakarta.models.ItemCarro`](src/main/java/es/daw/jakarta/models/ItemCarro.java)

### Vista

- **JSP y recursos estáticos**:  
  La parte visual se implementa con JSP y otros recursos web ubicados en:  
  - [src/main/webapp/index.jsp](src/main/webapp/index.jsp)  
  - Archivos adicionales como `login.html`, `carro.jsp`, etc., que permiten la interacción del usuario con la aplicación.

### Controladores

- **Servlets**:  
  Los controladores gestionan las solicitudes HTTP y definen la lógica de negocio para cada acción. Algunos ejemplos destacados son:  
  - [`es.daw.jakarta.controllers.LoginServlet`](src/main/java/es/daw/jakarta/controllers/LoginServlet.java): Gestiona el inicio de sesión, validando al usuario y redirigiendo a la vista adecuada.  
  - [`es.daw.jakarta.controllers.ExportarCarroServlet`](src/main/java/es/daw/jakarta/controllers/ExportarCarroServlet.java): Exporta el contenido del carrito a un archivo JSON.  
  - Otros servlets que permiten agregar, actualizar y eliminar productos o gestionar el carrito.

### Servicios

- **Lógica de negocio**:  
  Se encapsula la lógica de negocio en clases de servicio, por ejemplo:  
  - [`es.daw.jakarta.services.LoginService`](src/main/java/es/daw/jakarta/services/LoginService.java)  
  - [`es.daw.jakarta.services.LoginServiceImpl`](src/main/java/es/daw/jakarta/services/LoginServiceImpl.java)  
  - [`es.daw.jakarta.services.ProductService`](src/main/java/es/daw/jakarta/services/ProductService.java)

### Base de Datos y Acceso a Datos

- **DAO (Data Access Objects)**:  
  Los DAO gestionan la interacción con la base de datos, implementando operaciones CRUD sobre las entidades, por ejemplo:  
  - [`es.daw.jakarta.bd.DaoProducto`](src/main/java/es/daw/jakarta/bd/DaoProducto.java)  
  - [`es.daw.jakarta.bd.DaoCategoria`](src/main/java/es/daw/jakarta/bd/DaoCategoria.java)  
  - [`es.daw.jakarta.db.DBConnection`](src/main/java/es/daw/jakarta/db/DBConnection.java)

### Escucha de Eventos (Listeners)

- **Listeners de la aplicación**:  
  Los listeners permiten ejecutar código en respuesta a eventos del ciclo de vida del servlet y de las sesiones. Por ejemplo:  
  - [`es.daw.jakarta.listeners.AppListener`](src/main/java/es/daw/jakarta/listeners/AppListener.java): Gestiona eventos de inicialización de contexto, creación de sesión y seguimiento de atributos, lo que permite registrar actividades como el inicio y fin de sesión.

### Configuración y Construcción

- **Maven**:  
  El proyecto utiliza Maven para gestionar dependencias y la construcción del paquete WAR.  
  - El archivo principal [pom.xml](pom.xml) define las dependencias (por ejemplo, `jakarta.jakartaee-web-api`, `jackson-databind`, etc.) y plugins como el `maven-compiler-plugin` y el `maven-war-plugin`.

- **Maven Wrapper**:  
  Los scripts `mvnw` y `mvnw.cmd` permiten ejecutar Maven sin tenerlo instalado globalmente y validan la configuración del entorno (por ejemplo, la variable de entorno JAVA_HOME).

## Flujo de la Aplicación

1. **Inicio de Sesión**:  
   El usuario accede a la página de login (por ejemplo, `login.html`).  
   El [`LoginServlet`](src/main/java/es/daw/jakarta/controllers/LoginServlet.java) gestiona el inicio de sesión comprobando las credenciales (usuario y contraseña).  
   - En caso correcto, se crea una sesión y se establece el atributo `username`.
   - Se configura, opcionalmente, un cookie para personalizar ciertos aspectos de la vista (como el color de fondo).

2. **Navegación y Gestión del Carrito**:  
   Una vez autenticado, el usuario puede interactuar con el carrito, agregar o eliminar productos, y exportar el contenido del mismo en formato JSON mediante el [`ExportarCarroServlet`](src/main/java/es/daw/jakarta/controllers/ExportarCarroServlet.java).

3. **Interacción con la Base de Datos**:  
   Los DAO se encargan de obtener y persistir los datos relacionados con productos y categorías.

4. **Gestión del Ciclo de Vida**:  
   El [`AppListener`](src/main/java/es/daw/jakarta/listeners/AppListener.java) controla eventos importantes, como la inicialización y destrucción del contexto, y el manejo de sesiones para registrar eventos de log de autenticación.

Esta estructura separa claramente la lógica de negocio (modelo y servicios) de la presentación (vistas JSP) y del control de la aplicación (servlets y listeners), siguiendo una arquitectura orientada a capas y facilitando el mantenimiento y la evolución del proyecto.

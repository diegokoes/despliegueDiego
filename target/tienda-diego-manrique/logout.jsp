<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Logged-out</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" 
          rel="stylesheet" 
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" 
          crossorigin="anonymous">
    <style>
        body {
            background-color: #f8f9fa; /* Light gray background for minimalism */
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh; /* Full viewport height */
            margin: 0;
        }
        .logout-container {
            background-color: #ffffff; /* White background for the container */
            padding: 2rem;
            border-radius: 0.5rem;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* Subtle shadow */
            text-align: center;
            max-width: 500px; /* Maximum width for larger screens */
            width: 100%;
        }
        .logout-container h1 {
            margin-bottom: 1.5rem;
            font-size: 2rem;
            color: #343a40; /* Dark gray text */
        }
        .logout-container img {
            max-width: 100%;
            height: auto;
            margin-bottom: 1.5rem;
        }
        .logout-container a {
            display: inline-block;
            margin-top: 1rem;
            padding: 0.75rem 1.5rem;
            font-size: 1.25rem;
            color: #ffffff;
            background-color: #0d6efd; /* Bootstrap primary color */
            border-radius: 0.25rem;
            text-decoration: none;
            transition: background-color 0.3s ease;
        }
        .logout-container a:hover {
            background-color: #0b5ed7; /* Darker shade on hover */
        }
    </style>
</head>
<body>
    <div class="logout-container">
        <h1>Te has desloggeado</h1>
        <img src="images/sadcat.gif" alt="Gato triste" class="img-fluid">
        <a href="index.jsp">Volver a la página de inicio</a>
    </div>

    <!-- Bootstrap JS (optional, for interactive components) -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" 
            integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+3q4RYv6jIW3L3Z8q4h+2L6U00Jv6" 
            crossorigin="anonymous"></script>
</body>
</html>

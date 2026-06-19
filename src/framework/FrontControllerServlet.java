package framework;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FrontControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
        
        // 1. On récupère l'URL demandée par l'utilisateur
        String urlDemandee = request.getRequestURI();
        
        // 2. On nettoie l'URL pour savoir ce que l'utilisateur veut (ex: /accueil, /produits)
        String contexte = request.getContextPath();
        String action = urlDemandee.substring(contexte.length());

        //Juste retourner l'url
        response.getWriter().println("<h1>Tu as taper: "+action+"</h1>");
    }
}

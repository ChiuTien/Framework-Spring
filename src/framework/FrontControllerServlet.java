package framework;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import framework.interne.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FrontControllerServlet extends HttpServlet {

    // On crée une variable globale pour stocker nos contrôleurs en mémoire
    private List<Class<?>> controllersTrouves = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        System.out.println("[Framework] Initialisation en cours...");
        
        String packageAConfigurer = getServletConfig().getInitParameter("packageController");
        
        if (packageAConfigurer == null || packageAConfigurer.trim().isEmpty()) {
            System.err.println("[Framework] ERREUR : Le paramètre 'packageController' n'est pas configuré dans le web.xml !");
            return;
        }
        
        // On remplit notre liste globale au démarrage
        this.controllersTrouves = Scanner.getClassesAvecAnnotation(packageAConfigurer, Controller.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        traiterRequete(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        traiterRequete(request, response);
    }

    private void traiterRequete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // On configure le type de réponse en HTML avec encodage UTF-8
        response.setContentType("text/html;charset=UTF-8");
        
        // Le PrintWriter permet d'écrire du texte qui sera envoyé au navigateur
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>Statut du Framework</title></head>");
            out.println("<body>");
            out.println("<h1>[Framework] Diagnostic au démarrage</h1>");
            
            // On affiche le nombre de contrôleurs trouvés
            out.println("<p><strong>Nombre de contrôleurs détectés :</strong> " + controllersTrouves.size() + "</p>");
            out.println("<ul>");
            
            // On boucle sur notre liste stockée pour générer des puces HTML <li>
            for (Class<?> cls : controllersTrouves) {
                out.println("<li>Contrôleur trouvé : <code>" + cls.getName() + "</code></li>");
            }
            
            out.println("</ul>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
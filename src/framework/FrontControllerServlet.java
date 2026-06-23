package framework;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import framework.interne.Scanner;
import framework.interne.Route;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrontControllerServlet extends HttpServlet {

    private final Map<String, Route> dictionnaireRoutes = new HashMap<>();

    @Override
    public void init() throws ServletException {
        String packageAConfigurer = getServletConfig().getInitParameter("packageController");
        
        if (packageAConfigurer == null || packageAConfigurer.trim().isEmpty()) {
            System.err.println("[Framework] ERREUR : Le paramètre 'packageController' n'est pas configuré !");
            return;
        }
        
        List<Class<?>> controllers = Scanner.getClassesAvecAnnotation(packageAConfigurer, Controller.class);
        
        for (Class<?> cls : controllers) {
            for (Method methode : cls.getDeclaredMethods()) {
                if (methode.isAnnotationPresent(Url.class)) {
                    Url annotationUrl = methode.getAnnotation(Url.class);
                    String cheminUrl = annotationUrl.value();
                    
                    dictionnaireRoutes.put(cheminUrl, new Route(cls, methode));
                }
            }
        }
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
        
        response.setContentType("text/html;charset=UTF-8");
        
        // 1. Extraction de l'URL demandée par le navigateur
        String urlComplete = request.getRequestURI();
        String contexte = request.getContextPath();
        String urlDemandee = urlComplete.substring(contexte.length());

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html><html><head><title>Framework Router</title></head><body>");
            
            // 2. CAS 1 : L'URL demandée possède une méthode associée
            if (dictionnaireRoutes.containsKey(urlDemandee)) {
                Route routeAssociee = dictionnaireRoutes.get(urlDemandee);
                
                out.println("<h1 style='color: green;'>[Framework] Route Trouvée !</h1>");
                out.println("<p><strong>URL :</strong> <code>" + urlDemandee + "</code></p>");
                out.println("<p>associée à la méthode : <code>" + routeAssociee.getMethode().getName() + "()</code></p>");
                out.println("<p>dans la classe : <code>" + routeAssociee.getClasse().getName() + "</code></p>");
            } 
            // 3. CAS 2 : L'URL n'existe pas, on affiche toutes les routes disponibles
            else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("<h1 style='color: red;'>[Framework] 404 - Route introuvable</h1>");
                out.println("<p>L'URL <code>" + urlDemandee + "</code> n'est associée à aucune méthode.</p>");
                
                out.println("");
                out.println("<table border='1' cellpadding='10' style='border-collapse: collapse;'>");
                out.println("<tr style='background-color: #eee;'><th>URL</th><th>Classe</th><th>Méthode</th></tr>");
                
                // On boucle sur notre dictionnaire pour afficher tout ce qu'on a en mémoire
                for (Map.Entry<String, Route> entree : dictionnaireRoutes.entrySet()) {
                    String urlEnregistree = entree.getKey();
                    Route infoRoute = entree.getValue();
                    
                    out.println("<tr>");
                    out.println("  <td><code>" + urlEnregistree + "</code></td>");
                    out.println("  <td>" + infoRoute.getClasse().getName() + "</td>");
                    out.println("  <td><code>" + infoRoute.getMethode().getName() + "()</code></td>");
                    out.println("</tr>");
                }
                
                out.println("</table>");
            }
            
            out.println("</body></html>");
        }
    }
}
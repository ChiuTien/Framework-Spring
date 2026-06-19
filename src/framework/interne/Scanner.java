package framework.interne;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Scanner {

    public static List<Class<?>> getClassesAvecAnnotation(String nomPackage, Class<? extends java.lang.annotation.Annotation> annotation) {
        List<Class<?>> classesAnnotees = new ArrayList<>();
        
        try {
            // Convertit le nom du package en chemin de dossier (ex: "com.monapp" -> "com/monapp")
            String chemin = nomPackage.replace('.', '/');
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader(); 
            URL ressource = classLoader.getResource(chemin);

            if (ressource == null) {
                System.out.println("[Framework] Package introuvable : " + nomPackage);
                return classesAnnotees;
            }

            // Récupère le dossier physique des fichiers .class
            File dossier = new File(ressource.getFile());
            if (dossier.exists() && dossier.isDirectory()) {
                for (File fichier : dossier.listFiles()) {
                    // On ne prend que les fichiers .class
                    if (fichier.getName().endsWith(".class")) {
                        // Récupère le nom de la classe sans l'extension .class
                        String nomClasse = nomPackage + "." + fichier.getName().substring(0, fichier.getName().length() - 6);
                        
                        // Charge la classe en mémoire
                        Class<?> cls = Class.forName(nomClasse);
                        
                        // Vérifie si la classe possède l'annotation
                        if (cls.isAnnotationPresent(annotation)) {
                            classesAnnotees.add(cls);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return classesAnnotees;
    }
}
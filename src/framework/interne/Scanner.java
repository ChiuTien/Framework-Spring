package framework.interne;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Scanner {

    public static List<Class<?>> getClassesAvecAnnotation(String nomPackage, Class<? extends java.lang.annotation.Annotation> annotation) {
        List<Class<?>> classesAnnotees = new ArrayList<>();
        
        try {
            String chemin = nomPackage.replace('.', '/');
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader(); 
            URL ressource = classLoader.getResource(chemin);

            if (ressource == null) {
                System.out.println("[Framework] Package introuvable : " + nomPackage);
                return classesAnnotees;
            }

            File dossier = new File(ressource.getFile());
            if (dossier.exists() && dossier.isDirectory()) {
                for (File fichier : dossier.listFiles()) {

                    if (fichier.getName().endsWith(".class")) {
                        String nomClasse = nomPackage + "." + fichier.getName().substring(0, fichier.getName().length() - 6);
                        
                        Class<?> cls = Class.forName(nomClasse);
                        
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
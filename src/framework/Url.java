package framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Visible à l'exécution
@Retention(RetentionPolicy.RUNTIME)
// S'applique uniquement sur les MÉTHODES
@Target(ElementType.METHOD)
public @interface Url {
    // Permet de passer l'URL en paramètre, ex: @Url(value = "/accueil")
    // Le "default" évite de bloquer si on ne met rien
    String value() default "";
}
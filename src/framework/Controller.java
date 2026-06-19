package framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Indique que l'annotation sera visible à l'exécution (Runtime)
@Retention(RetentionPolicy.RUNTIME)
// Indique que l'annotation s'applique uniquement sur des classes
@Target(ElementType.TYPE)
public @interface Controller {
    // Optionnel : tu pourras ajouter des attributs plus tard (ex: une route)
}
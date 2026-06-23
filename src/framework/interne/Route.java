package framework.interne;

import java.lang.reflect.Method;

public class Route {
    private final Class<?> classe;
    private final Method methode;

    public Route(Class<?> classe, Method methode) {
        this.classe = classe;
        this.methode = methode;
    }

    public Class<?> getClasse() { return classe; }
    public Method getMethode() { return methode; }
}
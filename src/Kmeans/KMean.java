package Kmeans;

import java.lang.reflect.ParameterizedType;
import java.util.HashSet;


public abstract class KMean<T>{
    public int k ;
    private final Class<T> type;
    public HashSet<T> elts;
    public HashSet<T> centres;

    protected abstract void initialiserCentres();
    protected abstract boolean centresEgaux();
    protected abstract void calculer();

    public void prochaineEtape(){
        this.calculer();
    }

    public Class<T> getType(){ return this.type; }

    KMean(int k, HashSet<T> elements, Class<T> type){
        this.k = k;
        this.type = type;
        this.elts = elements;
        this.centres = new HashSet<>();
        initialiserCentres();
    }

    KMean(){
        this.k = 2;
        this.elts = new HashSet<>();
        this.centres = new HashSet<>();

        // Généré par ChatGPT
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        initialiserCentres();
    }

}
package Kmeans;

import java.lang.reflect.ParameterizedType;
import java.util.HashSet;


public abstract class KMean<T>{
    public int k ;
    public HashSet<T> elts;
    public HashSet<T> centres;

    protected abstract void initialiserCentres();
    protected abstract boolean centresEgaux();
    protected abstract void calculer();

    public void prochaineEtape(){
        this.calculer();
    }

    KMean(int k, HashSet<T> elements){
        this.k = k;
        this.elts = elements;
        this.centres = new HashSet<>();
        initialiserCentres();
    }

    KMean(){
        this.k = 2;
        this.elts = new HashSet<>();
        this.centres = new HashSet<>();
    }

}
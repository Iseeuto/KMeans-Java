package Kmeans;

import java.util.HashSet;


abstract class KMean<T>{
    int k ;
    HashSet<T> elts;
    HashSet<T> centres;

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
        initialiserCentres();
    }

}
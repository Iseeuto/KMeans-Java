package Kmeans;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class KMean<T>{
    int k ;
    HashSet<T> elts;
    HashSet<T> centres;
    ArrayList<HashSet<T>> groupes;
    static float SEUIL_CONVERGENCE = 0.1f;

    protected abstract void initialiserCentres();
    protected abstract void MAJGroupes();
    protected abstract boolean MAJCentres();

    protected abstract void calculer();

    public void prochaineEtape(){
        this.calculer();
    }

    KMean(int k, HashSet<T> elements){
        /* TO DO : empêcher que k soit supérieur au nombre de points*/
        this.k = k;
        this.elts = elements;
        this.centres = new HashSet<>();
        groupes = new ArrayList<>(k);
        for (int i = 0; i < this.k; i++) {
        groupes.add(new HashSet<>());}
        initialiserCentres();
    }

    KMean(){
        this.k = 2;
        this.elts = new HashSet<>();
        this.centres = new HashSet<>();
        groupes = new ArrayList<>(k);
        for (int i = 0; i < this.k; i++) {
        groupes.add(new HashSet<>());
        initialiserCentres();
    }

    }

}
package Kmeans;

import Formes.Point;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Classe abstraite représentant l'algorithme K-means générique pour un ensemble de données.
 * Elle permet de gérer l'initialisation des centres de clusters, l'attribution des points aux groupes,
 * ainsi que la mise à jour des centres jusqu'à ce qu'une condition de convergence soit atteinte.
 *
 * @param <T> Type des éléments à regrouper (par exemple, des points dans un espace à 2 dimensions).
 */
public abstract class KMean<T> {

    // Nombre de clusters
    int k;
    
    // Ensemble d'éléments à regrouper
    public HashSet<T> elts;
    
    // Ensemble des centres des clusters
    public HashSet<T> centres;
    
    // Liste des groupes (chaque groupe est un ensemble d'éléments)
    public ArrayList<Groupe> groupes;
    
    // Seuil de convergence, utilisé pour arrêter l'algorithme lorsque les centres ne changent plus beaucoup
    static float SEUIL_CONVERGENCE = 0.1f;

    /**
     * Calcule la distance entre deux points. Cette méthode est abstraite et doit être implémentée 
     * dans les classes dérivées pour calculer la distance selon la méthode spécifique (par exemple, 
     * distance euclidienne).
     * 
     * @param a Le premier point.
     * @param b Le second point.
     * @return La distance entre les deux points.
     */
    protected abstract float distance(Point a, Point b);

    /**
     * Initialisation des centres des clusters. Cette méthode est abstraite et doit être
     * implémentée dans les classes dérivées pour initialiser les centres selon la méthode spécifique
     * (par exemple, en choisissant aléatoirement des points ou en utilisant une autre stratégie).
     */
    protected abstract void initialiserCentres();

    /**
     * Met à jour les groupes (attribution des éléments aux clusters) en fonction des centres actuels.
     * Cette méthode est abstraite et doit être implémentée dans les classes dérivées pour attribuer
     * les points aux groupes en fonction de la distance par rapport aux centres.
     */
    protected abstract void MAJGroupes();

    /**
     * Met à jour les centres des clusters en fonction des groupes actuels. Les centres sont recalculés
     * en fonction des points qui leur sont associés.
     * 
     * @return true si les centres ont changé, false sinon.
     */
    protected abstract boolean MAJCentres();

    /**
     * Calcule une étape de l'algorithme K-means. Cette méthode gère l'attribution des points aux groupes
     * et la mise à jour des centres. Elle est appelée à chaque itération de l'algorithme.
     *
     * @return true si l'algorithme doit continuer, false sinon.
     */
    public abstract boolean calculer();

    /*Méthode permettant de renvoyé si les elements sont identiques
    *
    */
    private boolean elementsIdentiques(){
    //     if(elts.size() == 0){
    //         return true;
    //     }

    //     Iterator<T> it = elts.iterator();
    //     T cur = it.next();
    //     while(it.hasNext()){
    //         if(!cur )
    //     }
    return false;
    }

    /**
     * Constructeur principal de la classe KMean.
     * 
     * @param k Le nombre de clusters.
     * @param elements L'ensemble des éléments à regrouper.
     */
    KMean(int k, HashSet<T> elements) {
        //Scénario 1 : le nombre k est supérieur au nombre de point
        if (k > elements.size()) {
            throw new IllegalArgumentException("Le nombre de clusters k ne peut pas être supérieur au nombre de points.");
        }
        this.k = k;
        this.elts = elements;
        //Scénario 2 : Tous les points sont identiques
        if(elementsIdentiques()){
            throw new IllegalArgumentException("Les points doivent être différents pour pouvoir effectuer le k-mean");
        }
        this.centres = new HashSet<>();
        groupes = new ArrayList<>(k);
        for (int i = 0; i < this.k; i++) {
            groupes.add(new Groupe());
        }
        initialiserCentres();
    }

    /**
     * Constructeur par défaut de la classe KMean.
     * Ce constructeur initialise l'algorithme avec 2 clusters et un ensemble vide d'éléments.
     */
    KMean() {
        this.k = 2;
        this.elts = new HashSet<>();
        this.centres = new HashSet<>();
        groupes = new ArrayList<>(k);
        for (int i = 0; i < this.k; i++) {
            groupes.add(new Groupe());
        }
        initialiserCentres();
    }


}

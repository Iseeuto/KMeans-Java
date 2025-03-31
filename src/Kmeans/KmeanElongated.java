package Kmeans;

import Formes.Point;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Implémentation du KmeansElongated qui reprend les mêmes principes que le KmeanSimple mais avec une formule de distance différente
 * Seulement utilisable dans un espace en 2D
 */
public class KmeanElongated extends KMeanSimple{

    //Matrice contenant la moyenne de X, moyenne de Y, la variance de X, la variance de Y et la covariance de X et Y
    private HashMap<Groupe, Float[]> stats = new HashMap<>();

    private void moyenneGroupe(){
        for (int i = 0; i < k; i++) {
            Groupe g = this.groupes.get(i);
            float moyX = 0;
            float moyY = 0;
            HashSet<Point> pts= this.groupes.get(i).points ;
            for(Point p:pts){
                moyX += p.getX();
                moyY += p.getY();
            }
            stats.get(g)[0] = moyX/pts.size();
            stats.get(g)[1] = moyY/pts.size();
            }
    }

    private void varianceGroupe(){
        for (int i = 0; i < k; i++) {
            Groupe g = this.groupes.get(i);
            float varX = 0;
            float varY = 0;
            float coVar = 0;
            HashSet<Point> pts= g.points ;
            for(Point p:pts){
                varX += (float) Math.pow(p.getX() - stats.get(g)[0],2);
                varY += (float) Math.pow(p.getY() - stats.get(g)[1],2);
                coVar += (p.getX() - stats.get(g)[0])*(p.getY() - stats.get(g)[1]);
            }
            int taille = pts.size();
            stats.get(g)[2] = varX/(taille -1);
            stats.get(g)[3] = varY/(taille -1);
            stats.get(g)[4] = coVar/(taille -1);
            }
    }


    /**
     * Calcul de la distance avec la formule explicite de Mahalanobis en 2D
     */
    @Override
    protected float distance(Point p, Point centre){
        Groupe groupe = centre.groupe;
        float dx = p.getX() - centre.getX();
        float dy = p.getY() - centre.getY();

        return (float) Math.sqrt(
        (Math.pow(dx,2) * stats.get(groupe)[3]
        - 2 * dx * dy * stats.get(groupe)[4]
        + Math.pow(dy,2) * stats.get(groupe)[2]));
    }

    /**
     * Calcule une itération de l'algorithme K-means en mettant à jour les groupes et les centres.
     *
     * @return true si les centres doivent encore être mis à jour, false si l'algorithme a convergé
     */
    @Override
    public boolean calculer(){
        MAJGroupes();
        return  MAJCentres();
    }

    /**
     * Constructeur à deux paramètre avec un nombre k de clusters et un ensemble d'éléments.
     *
     * @param k Le nombre de clusters à créer
     * @param elements L'ensemble de points à regrouper
     */
    public KmeanElongated(int k,HashSet<Point> elements){
        super(k,elements);
    }

    /**
     * Constructeur par défaut du KmeanElongated sans paramètre
     */
    public KmeanElongated(){
        super();
    }

}
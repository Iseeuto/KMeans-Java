package Kmeans;

import Exceptions.IdenticalElementsException;
import Exceptions.MaxIterationException;
import Formes.Point;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Implémentation du KmeansElongated qui reprend les mêmes principes que le KmeanSimple mais avec une formule de distance différente
 * Seulement utilisable dans un espace en 2D
 */
public class KmeanElongated extends KMeanSimple{

    //Matrice contenant la moyenne de X, moyenne de Y, la variance de X, la variance de Y et la covariance de X et Y
    private HashMap<Groupe, Float[]> stats;

    private void moyenneGroupe(){
        for (int i = 0; i < k; i++) {
            Groupe g = this.groupes.get(i);
            float moyX = 0;
            float moyY = 0;
            ArrayList<Point> pts= this.groupes.get(i).points ;
            for(Point p:pts){
                moyX += p.getX();
                moyY += p.getY();
            }
            if(stats.get(g) == null){ stats.put(g, new Float[5]); }
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
            ArrayList<Point> pts= g.points ;
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
     * Calcul de la distance avec la formule explicite de Mahalanobis pour un espace 2D
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
     * Initialise les groupes avec une distance euclidienne 
     * pour pouvoir calculer par derrière les différentes statistiques nécessaire au calcul de distance de Mahalanobis
     */
    protected void InitGroupes(){
    // Vidage des groupes avant de pouvoir les remplir
        for (int i = 0; i < k; i++) {
            groupes.get(i).points.clear();
        }

        // Pour chaque point, on recherche le centre le plus proche et on l'ajoute dans le groupe correspondant
        for(Point p: elts){
            float min = Float.POSITIVE_INFINITY;
            Groupe meilleurGroupe = null;
            for (Point centre : centres) {
                float distance = Point.distanceEuclidienne(p, centre);
                if (distance < min){
                    min = distance;
                    meilleurGroupe = centre.groupe;
                }
            }
            meilleurGroupe.points.add(p);}}

    /**
     * Calcule une itération de l'algorithme K-means en mettant à jour les groupes et les centres.
     *
     */
    @Override
    public void calculer(){
        if(!this.seuilAtteint){
            MAJGroupes();
            try {
                this.seuilAtteint = !MAJCentres();
            } catch (MaxIterationException e){ System.out.println(e); }
        }
    }

    /**
     * Constructeur à deux paramètre avec un nombre k de clusters et un ensemble d'éléments.
     *
     * @param k Le nombre de clusters à créer
     * @param elements L'ensemble de points à regrouper
     */
    public KmeanElongated(int k,ArrayList<Point> elements) throws IdenticalElementsException {
        super(k,elements);
        this.stats = new HashMap<>();
        InitGroupes();
        moyenneGroupe();
        varianceGroupe();
    }

    /**
     * Constructeur par défaut du KmeanElongated sans paramètre
     */
    public KmeanElongated() throws IdenticalElementsException {
        super();
        this.stats = new HashMap<>();
        InitGroupes();
        moyenneGroupe();
        varianceGroupe();
    }

    public static void main(String[] args) {
        // Création d'un ensemble de points à tester
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(1, 2));
        points.add(new Point(2, 3));
        points.add(new Point(3, 3));
        points.add(new Point(8, 8));
        points.add(new Point(9, 7));
        points.add(new Point(10, 9));

        // Création d'une instance de KmeanElongated avec 2 clusters
        KmeanElongated kmeans;

        try{
            kmeans = new KmeanElongated(2, points);
        } catch (IdenticalElementsException e) { throw new RuntimeException(e); }
        
        // Initialisation des groupes
        kmeans.InitGroupes();

        // Vérification des moyennes des groupes
        System.out.println("Moyennes après l'initialisation :");
        kmeans.moyenneGroupe();
        for (int i = 0; i < 2; i++) {
            System.out.println("Groupe " + i + " - Moyenne X : " + kmeans.stats.get(kmeans.groupes.get(i))[0] + " | Moyenne Y : " + kmeans.stats.get(kmeans.groupes.get(i))[1]);
        }

        // Vérification des variances et covariances des groupes
        System.out.println("\nVariances et Covariances après l'initialisation :");
        kmeans.varianceGroupe();
        for (int i = 0; i < 2; i++) {
            System.out.println("Groupe " + i + " - Var(X): " + kmeans.stats.get(kmeans.groupes.get(i))[2] + " | Var(Y): " + kmeans.stats.get(kmeans.groupes.get(i))[3] + " | Cov(X,Y): " + kmeans.stats.get(kmeans.groupes.get(i))[4]);
        }

        // Vérification de l'assignation des points aux groupes
        System.out.println("\nGroupes après l'initialisation :");
        for (int i = 0; i < 2; i++) {
            System.out.print("Groupe " + i + ": ");
            for (Point p : kmeans.groupes.get(i).points) {
                System.out.print("x: " + p.getX() + "| y: " + p.getY() + " ");
            }
            System.out.println();
        }
        
        kmeans.executerKMeans();

        System.out.println("\nCentres finaux");
        for (Point centre : kmeans.centres) {
            System.out.println("x: " + centre.getX() + "| y: " + centre.getY());
        }
    }
}


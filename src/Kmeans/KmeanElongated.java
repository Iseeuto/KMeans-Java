package Kmeans;

import Formes.Point;
import java.util.HashSet;

/**
 * Implémentation du KmeansElongated qui reprend les mêmes principes que le KmeanSimple mais avec une formule de distance différente
 * Seulement utilisable dans un espace en 2D
 */
public class KmeanElongated extends KMeanSimple{

    //Matrice contenant la moyenne de X, moyenne de Y, la variance de X, la variance de Y et la covariance de X et Y
    private float[][] stats = new float[k][5];

    private void moyenneGroupe(){
        for (int i = 0; i < k; i++) {
            
            float moyX = 0;
            float moyY = 0;
            HashSet<Point> pts= this.groupes.get(i) ;
            for(Point p:pts){
                moyX += p.getX();
                moyY += p.getY();
            }
            stats[i][0] = moyX/pts.size();
            stats[i][1] = moyY/pts.size();
            }
    }

    private void varianceGroupe(){
        for (int i = 0; i < k; i++) {
            HashSet<Point> pts= this.groupes.get(i) ;
            int taille = pts.size();
            //Condition pour éviter une division par 0;
            if (taille > 1){
                    
                float varX = 0;
                float varY = 0;
                float coVar = 0;
                
                for(Point p:pts){
                    varX += (float) Math.pow(p.getX() - stats[i][0],2);
                    varY += (float) Math.pow(p.getY() - stats[i][1],2);
                    coVar += (p.getX() - stats[i][0])*(p.getY() - stats[i][1]);
                }
                
                stats[i][2] = varX/(taille -1);
                stats[i][3] = varY/(taille -1);
                stats[i][4] = coVar/(taille -1);}
            else{
                stats[i][2] = 0;
                stats[i][3] = 0;
                stats[i][4] = 0;
            }
            }
}


    /**
     * Calcul de la distance avec la formule explicite de Mahalanobis pour un espace 2D
     */
    @Override
    protected float distance(Point p, Point centre){
        int groupe = centre.groupe;
        float dx = p.getX() - centre.getX();
        float dy = p.getY() - centre.getY();

        return (float) Math.sqrt(
        (Math.pow(dx,2) * stats[groupe][3]  
        - 2 * dx * dy * stats[groupe][4]  
        + Math.pow(dy,2) * stats[groupe][2])); 
    }

    /**
     * Initialise les groupes avec une distance euclidienne 
     * pour pouvoir calculer par derrière les différentes statistiques nécessaire au calcul de distance de Mahalanobis
     */
    protected void InitGroupes(){
    // Vidage des groupes avant de pouvoir les remplir
        for (int i = 0; i < k; i++) {
            groupes.get(i).clear();
        }

        // Pour chaque point, on recherche le centre le plus proche et on l'ajoute dans le groupe correspondant
        for(Point p: elts){
            float min = Float.POSITIVE_INFINITY;
            int meilleurGroupe = -1;
            for (Point centre : centres) {
                float distance = Point.distanceEuclidienne(p, centre);
                if (distance < min){
                    min = distance;
                    meilleurGroupe = centre.groupe;
                }
            }
            this.groupes.get(meilleurGroupe).add(p);}}

    /**
     * Calcule une itération de l'algorithme K-means en mettant à jour les groupes et les centres.
     *
     * @param continuer Un booléen qui indique si l'algorithme doit continuer (si les centres n'ont pas convergé).
     * @return true si les centres doivent encore être mis à jour, false si l'algorithme a convergé
     */
    @Override
    protected boolean calculer(){
        moyenneGroupe();
        varianceGroupe();
        MAJGroupes();
        return MAJCentres();
        
    }

    /**
     * Constructeur à deux paramètre avec un nombre k de clusters et un ensemble d'éléments.
     *
     * @param k Le nombre de clusters à créer
     * @param elements L'ensemble de points à regrouper
     */
    public KmeanElongated(int k,HashSet<Point> elements){
        super(k,elements);
        this.stats = new float[k][5];
        InitGroupes();
        moyenneGroupe();
        varianceGroupe();
    }

    /**
     * Constructeur par défaut du KmeanElongated sans paramètre
     */
    public KmeanElongated(){
        super();
        this.stats = new float[k][5];
        InitGroupes();
        moyenneGroupe();
        varianceGroupe();
    }

    public static void main(String[] args) {
        // Création d'un ensemble de points à tester
        HashSet<Point> points = new HashSet<>();
        points.add(new Point(1, 2));
        points.add(new Point(2, 3));
        points.add(new Point(3, 3));
        points.add(new Point(8, 8));
        points.add(new Point(9, 7));
        points.add(new Point(10, 9));

        // Création d'une instance de KmeanElongated avec 2 clusters
        KmeanElongated kmeans = new KmeanElongated(2, points);
        
        // Initialisation des groupes
        kmeans.InitGroupes();

        // Vérification des moyennes des groupes
        System.out.println("Moyennes après l'initialisation :");
        kmeans.moyenneGroupe();
        for (int i = 0; i < 2; i++) {
            System.out.println("Groupe " + i + " - Moyenne X : " + kmeans.stats[i][0] + " | Moyenne Y : " + kmeans.stats[i][1]);
        }

        // Vérification des variances et covariances des groupes
        System.out.println("\nVariances et Covariances après l'initialisation :");
        kmeans.varianceGroupe();
        for (int i = 0; i < 2; i++) {
            System.out.println("Groupe " + i + " - Var(X): " + kmeans.stats[i][2] + " | Var(Y): " + kmeans.stats[i][3] + " | Cov(X,Y): " + kmeans.stats[i][4]);
        }

        // Vérification de l'assignation des points aux groupes
        System.out.println("\nGroupes après l'initialisation :");
        for (int i = 0; i < 2; i++) {
            System.out.print("Groupe " + i + ": ");
            for (Point p : kmeans.groupes.get(i)) {
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


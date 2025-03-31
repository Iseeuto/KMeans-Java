package Kmeans;

import Formes.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Implémentation de l'algorithme des K-means pour un ensemble de points en 2D.
 * Cette classe permet de regrouper les points en k clusters en utilisant la méthode des K-means.
 * La distance utilisée pour le regroupement est la distance euclidienne.
 * 
 * @see KMean
 * @see Point
 */
public class KMeanSimple extends KMean<Point>{

    /**
     * Initialise les centres des clusters en sélectionnant aléatoirement k points parmi les éléments.
     * Chaque centre est ajouté à l'ensemble des centres.
     */
    @Override
    protected void initialiserCentres(){
        int taille = this.elts.size();
        if(taille == 0) return;
        /*Transformation en liste pour pouvoir accéder aux différents points par leur indice*/
        ArrayList<Point> liste = new ArrayList<>(this.elts);
        Random random = new Random();
        int indice;
        int compteur = 0;
        while(compteur < k){
            indice = random.nextInt(taille);
            if (!this.centres.contains(liste.get(indice))){
                Point p = liste.get(indice);
                Point centre = new Point(p.getX(), p.getY());
                centre.setGroupe(compteur);
                this.centres.add(centre);
                compteur += 1;
            }
        }
    }

    /**
     * Calcule la distance euclidienne entre deux points.
     *
     * @param a Le premier point
     * @param b Le second point
     * @return La distance euclidienne entre les deux points
     */
    @Override
    protected float distance(Point a, Point b){
        return Point.distanceEuclidienne(a, b);
    }

    /**
     * Met à jour les groupes en attribuant chaque point au centre le plus proche.
     * Chaque point est ajouté au groupe de son centre le plus proche en fonction de la distance euclidienne.
     */
    @Override
    protected void MAJGroupes(){
        // Vidage des groupes avant de pouvoir les remplir
        for (int i = 0; i < k; i++) {
            groupes.get(i).clear();
        }

        // Pour chaque point, on recherche le centre le plus proche et on l'ajoute dans le groupe correspondant
        for(Point p: elts){
            float min = Float.POSITIVE_INFINITY;
            int meilleurGroupe = -1;
            for (Point centre : centres) {
                float distance = this.distance(p, centre);
                if (distance < min){
                    min = distance;
                    meilleurGroupe = centre.groupe;
                }
            }
            this.groupes.get(meilleurGroupe).add(p);
        }
    }

    /**
     * Met à jour les centres des clusters en recalculant la moyenne des coordonnées des points de chaque groupe.
     * La méthode retourne un booléen indiquant si les centres ont convergé, c'est-à-dire si le mouvement des centres est inférieur à un seuil.
     *
     * @return true si les centres ont bougé, false si la convergence est atteinte
     */
    @Override
    protected boolean MAJCentres(){
        boolean continuer = false;
        for (Point centre : centres) {
            int indice = centre.groupe;
            HashSet<Point> liste = groupes.get(indice);
            float taille = liste.size();
            float newX = 0;
            float newY = 0;
            for(Point p: liste){
                newX += p.getX();
                newY += p.getY();
            }
            newX = newX / taille; 
            newY = newY / taille;
            Point newCentre = new Point(newX, newY);
            if(Point.distanceEuclidienne(centre, newCentre) >= SEUIL_CONVERGENCE){
                continuer = true;
                centre.setX(newX);
                centre.setY(newY);
            }
        }
        return continuer;
    }

    /**
     * Exécute l'algorithme des K-means jusqu'à convergence. À chaque itération, les groupes sont mis à jour, puis les centres.
     */
    public void executerKMeans() {
        boolean continuer = true;
        while (continuer) {
            MAJGroupes();   // Mise à jour des groupes
            continuer = MAJCentres();  // Mise à jour des centres et vérification de la convergence
        }
    }

    /**
     * Calcule une itération de l'algorithme K-means en mettant à jour les groupes et les centres.
     *
     * @param continuer Un booléen qui indique si l'algorithme doit continuer (si les centres n'ont pas convergé).
     * @return true si les centres doivent encore être mis à jour, false si l'algorithme a convergé
     */
    @Override
    protected boolean calculer(){ 
        MAJGroupes();
        return MAJCentres();    
        }

    /**
     * Constructeur de la classe KMeanSimple avec un nombre k de clusters et un ensemble d'éléments.
     *
     * @param k Le nombre de clusters à créer
     * @param elements L'ensemble de points à regrouper
     */
    public KMeanSimple(int k, HashSet<Point> elements){
        super(k, elements);
    }

    /**
     * Constructeur par défaut qui initialise k à 2 et crée un ensemble vide de points.
     */
    public KMeanSimple(){
        super(2, new HashSet<>());
    }

    /**
     * Point d'entrée pour tester l'algorithme K-means avec des exemples de points.
     *
     * @param args Arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        Point a = new Point(1, 1);
        Point b = new Point(2, 2);
        Point c = new Point(3, 1);
        Point d = new Point(6, 5);
        Point e = new Point(7, 6);
        Point f = new Point(8, 5);
        Point g = new Point(9, 8);
        Point h = new Point(10, 7);

        HashSet<Point> elts = new HashSet<>();

        elts.add(a);
        elts.add(b);
        elts.add(c);
        elts.add(d);
        elts.add(e);
        elts.add(f);
        elts.add(g);
        elts.add(h);

        KMeanSimple test = new KMeanSimple(3, elts);

        System.out.println("Liste des centres :");
        for(Point p:test.centres){
            System.out.println(p);
        }

        test.MAJGroupes();

        for(int i = 0; i < test.k; i++){
            HashSet<Point> liste = test.groupes.get(i);
            System.out.printf("Groupe %d%n", i);
            for (Point p: liste){
                System.out.println(p);
            }
        }

        KMeanSimple test1 = new KMeanSimple(3, elts);
        test1.executerKMeans();

        System.out.println("Liste des centres :");
        for(Point p:test1.centres){
            System.out.println(p);
        }
        System.err.println("");

        for(int i = 0; i < test1.k; i++){
            HashSet<Point> liste = test1.groupes.get(i);
            System.out.printf("Groupe %d%n", i);
            for (Point p: liste){
                System.out.println(p);
            }
        }
    }
}

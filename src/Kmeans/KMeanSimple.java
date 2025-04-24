package Kmeans;

import Exceptions.IdenticalElementsException;
import Exceptions.MaxIterationException;
import Formes.Point;

import java.util.ArrayList;
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
            Point p = liste.get(indice);
            if (!this.centres.contains(p)){
                Point centre = new Point(p.getX(), p.getY());
                Groupe g = new Groupe();
                centre.setGroupe(g);
                this.centres.add(centre);
                this.groupes.add(g);
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
        for(Groupe g : this.groupes) g.points.clear();

        // Pour chaque point, on recherche le centre le plus proche et on l'ajoute dans le groupe correspondant
        for(Point p: elts){
            float min = Float.POSITIVE_INFINITY;
            Groupe meilleurGroupe = null;
            for (Point centre : centres) {
                float distance = this.distance(p, centre);
                if (distance < min){
                    min = distance;
                    meilleurGroupe = centre.getGroupe();
                }
            }
            meilleurGroupe.points.add(p);
            p.setGroupe(meilleurGroupe);
        }
    }

    /**
     * Met à jour les centres des clusters en recalculant la moyenne des coordonnées des points de chaque groupe.
     * La méthode retourne un booléen indiquant si les centres ont convergé, c'est-à-dire si le mouvement des centres est inférieur à un seuil.
     *
     * @return true si les centres ont bougé, false si la convergence est atteinte
     */
    @Override
    protected boolean MAJCentres() throws MaxIterationException {
        boolean continuer = false;
        int iterations = 0;
        for (Point centre : centres) {
            // Erreur si le nombre maximum d'itérations est atteint
            iterations++;
            if(iterations > MAX_ITERATION) throw new MaxIterationException();

            ArrayList<Point> liste = centre.getGroupe().points;
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
        while(!seuilAtteint) calculer();
    }

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
     * Constructeur de la classe KMeanSimple avec un nombre k de clusters et un ensemble d'éléments.
     *
     * @param k Le nombre de clusters à créer
     * @param elements L'ensemble de points à regrouper
     */
    public KMeanSimple(int k, ArrayList<Point> elements) throws IdenticalElementsException {
        super(k, elements);
    }

    /**
     * Constructeur par défaut qui initialise k à 2 et crée un ensemble vide de points.
     */
    public KMeanSimple() throws IdenticalElementsException {
        super(2, new ArrayList<>());
    }

    /**
     * Point d'entrée pour tester l'algorithme K-means avec des exemples de points.
     *
     * @param args Arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        Point b = new Point(2, 2);
        Point c = new Point(2, 2);
        Point d = new Point(2, 2);
        Point e = new Point(2, 2);
        Point f = new Point(2, 2);

        ArrayList<Point> elts = new ArrayList<>();

        elts.add(b);
        elts.add(c);
        elts.add(d);
        elts.add(e);
        elts.add(f);

        KMeanSimple test;

        try {
            test = new KMeanSimple(1, elts);
        } catch (IdenticalElementsException exception){ System.out.println(exception); return; }

        System.out.println("Liste des centres :");
        for(Point p:test.centres){
            System.out.println(p);
        }

        test.MAJGroupes();

        //Test pour voir si l'execution du Kmeans marche
        int i =0;
        for(Groupe gr: test.groupes) System.out.println("GROUPE " + i++ + ": " + gr);

        KMeanSimple test1;

        try {
            test1 = new KMeanSimple(3, elts);
        } catch(IdenticalElementsException exception){ throw new RuntimeException(exception); }

        test1.executerKMeans();

        System.out.println("Liste des centres :");
        for(Point p:test1.centres){
            System.out.println(p);
        }
        System.err.println("");

        i=0;
        for(Groupe gr: test1.groupes) System.out.println("GROUPE " + i++ + ": " + gr);

        //Test pour vérifier qu'une exception est bien levé quand k est supérieur au nombre de points
        try {
            ArrayList<Point> points = new ArrayList<>();
            points.add(new Point(1, 2));
            points.add(new Point(2, 3));
            points.add(new Point(3, 3));

            // Test avec k supérieur au nombre de points (ici, k = 5 alors qu'il n'y a que 3 points)
            int k = 5;
            KMeanSimple kmeans;

            try {
                kmeans = new KMeanSimple(k, points);
            } catch(IdenticalElementsException exception){ throw new RuntimeException(exception); }

        } catch (IllegalArgumentException exp) {
            System.out.println("\nException attrapée : " + exp.getMessage());
        }
    }
}

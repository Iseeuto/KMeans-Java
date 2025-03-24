package Kmeans;

import Formes.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class KMeanSimple extends KMean<Point>{

    /**
     * Méthode renvoyant k points aléatoires dans la liste de points ("elts")
     * qui seront utilisés comme les premiers centre de l'algorithme des KMeans
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
        while(compteur<k){

            indice = random.nextInt(taille);
            if (!this.centres.contains(liste.get(indice))){
                Point p = liste.get(indice);
                Point centre = new Point(p.getX(),p.getY());
                centre.setGroupe(compteur);
                this.centres.add(centre);
                compteur+=1;
            }   
            }  
     }

    /* Rempli les groupes avec leurs points les plus proches en utilisant la distance euclidienne
     */
    @Override
    protected void MAJGroupes(){

        // Vidage des groupes avant de pouvoir les remplir
        for (int i = 0; i < k; i++) {
            groupes.get(i).clear();
        }

        // Pour chaque point on recherche à quel centre il est le plus proche et on l'ajoute dans le groupe correspondant
        for(Point p: elts){
            float min = Float.POSITIVE_INFINITY;
            int meilleurGroupe = -1;
            for (Point centre : centres) {
                float distance = p.distanceEuclidienne(centre);
                if (distance < min){
                    min = distance;
                    meilleurGroupe = centre.groupe;
                }
            }
            this.groupes.get(meilleurGroupe).add(p);
        }
    }

    /**
     * Met à jour les centres et renvoie un booléen qui indique si les centres ont convergés, et donc indique si l'on est arrivé à la fin 
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
            for(Point p:liste){
                newX += p.getX();
                newY += p.getY();
            }
            newX = newX/taille; newY = newY/taille;
            Point newCentre = new Point(newX,newY);
            if(centre.distanceEuclidienne(newCentre)>=SEUIL_CONVERGENCE){
                continuer = true;
                centre = newCentre;
            }
        }
        return continuer;
    }

    
    @Override
    protected void calculer(){ return; }

    public KMeanSimple(int k, HashSet<Point> elements){
        super(k, elements);
    }

    public KMeanSimple(){
        super(2, new HashSet<>());
    }



    public static void main(String[] args) {
        Point a = new Point(0,3);
        Point b = new Point(5,7);
        Point c = new Point(6,6);
        Point d = new Point(7,7);
        Point e = new Point(8,8);
        Point f = new Point(9,9);

        HashSet<Point> elts = new HashSet<>();

        elts.add(a);
        elts.add(b);
        elts.add(c);
        elts.add(d);
        elts.add(e);
        elts.add(f);

        KMeanSimple test = new KMeanSimple(3,elts);

        System.out.println("Liste des centres :");
        for(Point p:test.centres){
            System.out.println(p);
        }

        test.MAJGroupes();

        for(int i=0;i<test.k;i++){
            HashSet<Point> liste = test.groupes.get(i);
            System.out.printf("Groupe %d%n",i);
            for (Point p: liste){
                System.out.println(p);
            }
        }

    }
}


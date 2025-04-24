package Formes;

import java.util.HashSet;
import java.util.Random;

/**
 * Classe représentant une forme géométrique de base.
 * Cette classe est destinée à être étendue par d'autres classes représentant des formes spécifiques, 
 * comme un cercle ou une ellipse.
 * 
 * @see Cercle
 * @see Ellipse
 */
public class Forme {

    // Centre de la Forme
    protected Point centre;

    // Points composant la forme
    protected HashSet<Point> points;

    /**
     * Constructeur de la classe Forme avec un point spécifiant le centre.
     * 
     * @param centre Le point représentant le centre de la forme.
     */
    Forme(Point centre){
        this.centre = centre;
    }

    /**
     * Constructeur par défaut de la classe Forme.
     * Ce constructeur initialise une forme aléatoire.
     */
    Forme(){
        Random rand = new Random();
        this.centre = new Point(rand.nextInt(500), rand.nextInt(500));
        this.points = new HashSet<>();

        int nb_points = rand.nextInt(100);
        for(int i=0; i<nb_points;i++){
            double angle = Math.random()*360;
            double dist = Math.random()*100;
            this.points.add(new Point((float) (centre.getX() + (dist* Math.toRadians(angle))), (float) (centre.getY() + (dist* Math.toRadians(angle)))));
        }
    }

    /**
     * Retourne le point représentant le centre de la forme.
     * 
     * @return Le centre de la forme sous forme d'un objet Point.
     */
    public Point getCentre(){
        return this.centre;
    }

    /**
     * Retourne une représentation sous forme de chaîne de la forme.
     * Cette représentation inclut le centre de la forme.
     * 
     * @return Une chaîne représentant la forme avec son centre.
     */
    @Override
    public String toString(){
        return "Centre (" + this.centre + ")";
    }

    /**
     * Méthode principale pour tester l'instanciation de formes avec différents centres.
     * 
     * @param args Les arguments de la ligne de commande (non utilisés ici).
     */
    public static void main(String[] args) {
        Forme F1 = new Forme(new Point(5, 5));
        Forme F2 = new Forme();

        System.out.println(F1);
        System.out.println(F2);
    }
}
package Formes;

import Kmeans.Groupe;

import java.util.Random;

/**
 * Classe représentant un point dans un espace à deux dimensions.
 * Cette classe permet de créer des objets représentant des points avec des coordonnées x et y, ainsi qu'un groupe associé.
 * Elle contient des méthodes pour obtenir et modifier les coordonnées du point, ainsi que des méthodes pour comparer deux points.
 */
public class Point {

    // Coordonnées x et y du point
    private float x;
    private float y;

    // Groupe d'appartenance du point
    public Groupe groupe;

    /**
     * Constructeur de la classe Point avec deux paramètres de coordonnées.
     * Ce constructeur initialise un point avec les coordonnées spécifiées x et y. Le groupe est initialisé à -1 par défaut.
     * 
     * @param x Coordonnée x du point.
     * @param y Coordonnée y du point.
     */
    public Point(float x, float y){
        this.x = x;
        this.y = y;
        this.groupe = null;
    }

    /**
     * Constructeur par défaut de la classe Point.
     * Ce constructeur initialise un point avec des coordonnées aléatoires et le groupe à null par défaut.
     */
    Point(){
        this.x = 0;
        this.y = 0;
        this.groupe = null;
    }

    /**
     * Retourne la coordonnée x du point.
     * 
     * @return La valeur de la coordonnée x du point.
     */
    public float getX(){
        return x;
    }

    /**
     * Retourne la coordonnée y du point.
     * 
     * @return La valeur de la coordonnée y du point.
     */
    public float getY(){
        return y;
    }

    /**
     * Modifie la coordonnée x du point.
     * 
     * @param x La nouvelle valeur de la coordonnée x.
     */
    public void setX(float x){
        this.x = x;
    }

    /**
     * Modifie la coordonnée y du point.
     * 
     * @param y La nouvelle valeur de la coordonnée y.
     */
    public void setY(float y){
        this.y = y;
    }

    /**
     * Retourne le groupe auquel le point appartient.
     * 
     * @return Le groupe du point.
     */
    public Groupe getGroupe(){
        return this.groupe;
    }

    /**
     * Modifie le groupe du point.
     * 
     * @param g Le groupe à assigner au point.
     */
    public void setGroupe(Groupe g){
        this.groupe = g;
    }

    /**
     * Retourne une représentation sous forme de chaîne du point.
     * 
     * @return Une chaîne représentant le point, avec ses coordonnées x et y.
     */
    @Override
    public String toString(){
        return "x: " + this.getX() + "| y: " + this.getY();
    }

    /**
     * Redéfinition de la méthode equals pour comparer deux points.
     * Cette méthode considère deux points égaux s'ils ont les mêmes coordonnées x et y.
     * 
     * @param obj L'objet à comparer avec l'instance courante.
     * @return true si les deux points ont les mêmes coordonnées, sinon false.
     */
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true; // Vérification de la référence
        if (obj == null || getClass() != obj.getClass()) return false; // Vérification du type
        Point p = (Point) obj;
        return p.x == this.x && p.y == this.y;
    }

    /**
     * Redéfinition de la méthode hashCode pour garantir une cohérence avec la méthode equals.
     * Cette méthode calcule un code de hachage basé sur les coordonnées du point.
     * Elle est nécessaire pour utiliser des méthodes comme contains et equals sur des HashSet<Point>.
     * 
     * @return Le code de hachage du point.
     */
    @Override
    public int hashCode() {
        return 31 * Float.hashCode(x) + Float.hashCode(y);
    }

    /**
     * Calcule la distance euclidienne entre deux points.
     *
     * @param a Le premier point
     * @param b Le second point
     * @return La distance euclidienne entre les deux points
     */
    static public float distanceEuclidienne(Point a,Point b){
        return (float) Math.sqrt(Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2));
    }

    /**
     * Méthode principale pour tester l'égalité de deux points.
     * 
     * @param args Les arguments de la ligne de commande (non utilisés ici).
     */
    public static void main(String[] args){
        Point a = new Point(0,3);
        Point b = new Point(5,7);
        Point c = new Point(0,3);
        
        System.out.println(a.equals(b)); // Affiche false
        System.out.println(c.equals(a)); // Affiche true
    }
}

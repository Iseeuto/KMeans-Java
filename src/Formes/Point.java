package Formes;

public class Point {

    /**
     * Classe qui représente un point en 2 dimensions
     */
    private float x;
    private float y;

    /**
     * Constructeur de base à 2 paramètres
     * @param x float représentant la coordonnée x du point
     * @param y float représentant la coordonnée y du point
     */

    Point(float x, float y){
        this.x = x;
        this.y = y;
    }

    Point(){
        this.x = 0;
        this.y = 0;
    }

    /**
     * Getter de x
     * @return la valeur de x
     */
    public float getX(){return x;}

    /**
     * Getter de y
     * @return la valeur de y
     */
    public float getY(){return y;}

    /** Méthode qui renvoie la distance euclidienne entre notre point et un autre
     *
     * @param b : autre point avec lequel on calcule la distance de type Point
     * @return : un float étant la distance euclidienne
     */
    public float distanceEuclidienne(Point b){
        return (float) Math.sqrt(Math.pow(b.getX()-this.getX(),2)+Math.pow(b.getY()-this.getY(),2));
    }

    @Override
    public String toString(){ return "x: " + this.getX() + "| y: " + this.getY(); }

    public static void main(String[] args){
        Point a = new Point(0,3);
        Point b = new Point(5,7);

        System.out.println(a.distanceEuclidienne(b));
    }

}

package Formes;

/** Classe basique d'un point dans un espace en deux dimensions
 */
public class Point {

    private float x;
    private float y;

    public int groupe;

    /**
     * Constructeur de base à 2 paramètres
     * @param x float représentant la coordonnée x du point
     * @param y float représentant la coordonnée y du point
     */

    public Point(float x, float y){
        this.x = x;
        this.y = y;
        this.groupe = -1;
    }

    Point(){
        this.x = 0;
        this.y = 0;
        this.groupe = -1;
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

    public void setX(float x){this.x=x;}

    public void setY(float y){this.y=y;}

    /**
     * Getter du groupe
     * @return le label du groupe
     */

    /** Méthode qui renvoie la distance euclidienne entre notre point et un autre
     *
     * @param b : autre point avec lequel on calcule la distance de type Point
     * @return : un float étant la distance euclidienne
     */
    public float distanceEuclidienne(Point b){
        return (float) Math.sqrt(Math.pow(b.getX()-this.getX(),2)+Math.pow(b.getY()-this.getY(),2));
    }

    public int getGroupe(){ return this.groupe; }
    public void setGroupe(int g){ this.groupe = g; }

    @Override
    public String toString(){ return "x: " + this.getX() + "| y: " + this.getY(); }

    /**
     * Fait avec chat GPT, l'override du equals et du hashcode permet d'utiliser les méthodes contains et equals sur les HashSet<Point>
     * et considère des points égaux si ils possèdent les mêmes coordonnées
     */
    @Override
    public boolean equals(Object obj){
        Point p = (Point) obj;
        return p.x == this.x && p.y == this.y;
    }

    @Override
    public int hashCode() {
        return 31 * Float.hashCode(x) + Float.hashCode(y);
    }

    public static void main(String[] args){
        Point a = new Point(0,3);
        Point b = new Point(5,7);
        Point c = new Point(0,3);

        System.out.println(a.distanceEuclidienne(b));
        
        System.out.println(a.equals(b));
        System.out.println(c.equals(a));
    }



}

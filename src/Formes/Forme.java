package Formes;

/** 
 * Classe représentant une forme géométrique de base.
 * Cette classe est destinée à être étendue par d'autres classes représentant des formes spécifiques, 
 * comme un cercle ou une ellipse.
 * 
 * @see Cercle
 * @see Ellipse
 */
public class Forme {

    protected Point centre;

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
     * Ce constructeur initialise la forme avec un centre à (0, 0).
     */
    Forme(){
        this.centre = new Point();
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
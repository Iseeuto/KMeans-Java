package Formes;

/** Classe basique d'une forme
 * @see Cercle
 * @see Ellipse
 */
public class Forme {

    private Point centre;

    /**
     * Getter de centre
     * @return point faisant office de centre
     */
    public Point getCentre(){ return this.centre; }

    Forme(Point centre){ this.centre = centre; }
    Forme(){ this.centre = new Point(); }

    public static void main(String[] args) {
        Forme F1 = new Forme(new Point(5, 5));
        Forme F2 = new Forme();

        System.out.println(F1.getCentre());
        System.out.println(F2.getCentre());
    }
}



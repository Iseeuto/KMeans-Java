package Formes;

/** Classe basique d'une forme
 * @see Cercle
 * @see Ellipse
 */
public class Forme {

    protected Point centre;

    /**
     * Getter de centre
     * @return point faisant office de centre
     */
    public Point getCentre(){ return this.centre; }

    @Override
    public String toString(){ return "Centre ("+this.centre + ")"; }

    Forme(Point centre){ this.centre = centre; }
    Forme(){ this.centre = new Point(); }

    public static void main(String[] args) {
        Forme F1 = new Forme(new Point(5, 5));
        Forme F2 = new Forme();

        System.out.println(F1);
        System.out.println(F2);
    }
}



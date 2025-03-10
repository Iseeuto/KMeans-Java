package Formes;

/**
 * Classe décrivant une ellipse
 */
public class Ellipse extends Forme{

    private Point AxeMajeur, AxeMineur;

    /**
     * Getter de l'axe majeur de l'ellipse
     * @return point faisant office d'axe majeur
     */
    public Point getAxeMajeur() { return AxeMajeur; }

    /**
     * Getter de l'axe mineur de l'ellipse
     * @return point faisant office d'axe mineur
     */
    public Point getAxeMineur() { return AxeMineur; }

    @Override
    public String toString(){ return super.toString() + ", AxeMineur (" + this.AxeMineur + "), AxeMajeur (" + this.AxeMajeur + ")"; }

    Ellipse(Point centre, Point majeur, Point mineur){
        super(centre);
        this.AxeMajeur = majeur;
        this.AxeMineur = mineur;
    }

    Ellipse(){
        super();
        this.AxeMajeur = new Point(0, 1);
        this.AxeMineur = new Point(0, -1);
    }

    public static void main(String[] args) {
        Ellipse E1 = new Ellipse(new Point(1, 4), new Point(1,5), new Point(1, 3));
        Ellipse E2 = new Ellipse();

        System.out.println(E1);
        System.out.println(E2);
    }

}

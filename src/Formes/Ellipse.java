package Formes;

/**
 * Classe décrivant une ellipse
 */
public class Ellipse extends Forme{

    private float AxeMajeur, AxeMineur;

    /**
     * Getter de l'axe majeur de l'ellipse
     * @return longueur de l'axe majeur
     */
    public float getAxeMajeur() { return AxeMajeur; }

    /**
     * Getter de l'axe mineur de l'ellipse
     * @return longueur de l'axe mineur
     */
    public float getAxeMineur() { return AxeMineur; }

    @Override
    public String toString(){ return super.toString() + ", AxeMineur (" + this.AxeMineur + "), AxeMajeur (" + this.AxeMajeur + ")"; }

    Ellipse(Point centre, float majeur, float mineur){
        super(centre);
        this.AxeMajeur = majeur;
        this.AxeMineur = mineur;
    }

    Ellipse(){
        super();
        this.AxeMajeur = 1;
        this.AxeMineur = 1;
    }

    public static void main(String[] args) {
        Ellipse E1 = new Ellipse(new Point(1, 4), 4, 2);
        Ellipse E2 = new Ellipse();

        System.out.println(E1);
        System.out.println(E2);
    }

}

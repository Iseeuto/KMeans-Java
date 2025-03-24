package Formes;

/**
 * Classe représentant une ellipse.
 * Cette classe hérite de la classe Forme et ajoute des attributs pour les axes majeur et mineur.
 * Elle permet de définir et manipuler une ellipse en 2D, avec un centre et deux axes (majeur et mineur).
 * 
 * @see Forme
 */
public class Ellipse extends Forme {

    private float AxeMajeur, AxeMineur;

    /**
     * Constructeur de la classe Ellipse avec un centre et des valeurs pour les axes majeur et mineur spécifiés.
     * 
     * @param centre Le point représentant le centre de l'ellipse.
     * @param majeur La longueur de l'axe majeur de l'ellipse.
     * @param mineur La longueur de l'axe mineur de l'ellipse.
     */
    Ellipse(Point centre, float majeur, float mineur){
        super(centre);
        this.AxeMajeur = majeur;
        this.AxeMineur = mineur;
    }

    /**
     * Constructeur par défaut de la classe Ellipse.
     * Ce constructeur initialise une ellipse avec un centre à (0, 0), un axe majeur de 1 et un axe mineur de 1.
     */
    Ellipse(){
        super();
        this.AxeMajeur = 1;
        this.AxeMineur = 1;
    }

    /**
     * Retourne la longueur de l'axe majeur de l'ellipse.
     * 
     * @return La longueur de l'axe majeur de l'ellipse.
     */
    public float getAxeMajeur() {
        return AxeMajeur;
    }

    /**
     * Retourne la longueur de l'axe mineur de l'ellipse.
     * 
     * @return La longueur de l'axe mineur de l'ellipse.
     */
    public float getAxeMineur() {
        return AxeMineur;
    }

    /**
     * Retourne une représentation sous forme de chaîne de l'ellipse.
     * Cette représentation inclut le centre ainsi que les longueurs des axes majeur et mineur de l'ellipse.
     * 
     * @return Une chaîne représentant l'ellipse avec son centre et ses axes.
     */
    @Override
    public String toString(){
        return super.toString() + ", AxeMineur (" + this.AxeMineur + "), AxeMajeur (" + this.AxeMajeur + ")";
    }

    /**
     * Méthode principale pour tester l'instanciation d'ellipses avec différents centres et axes.
     * 
     * 
     */
    public static void main(String[] args) {
        Ellipse E1 = new Ellipse(new Point(1, 4), 4, 2);
        Ellipse E2 = new Ellipse();

        System.out.println(E1);
        System.out.println(E2);
    }
}

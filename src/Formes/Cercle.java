package Formes;

/**
 * Classe représentant un cercle.
 * Cette classe hérite de la classe Forme et ajoute un attribut pour le rayon.
 * Elle permet de définir et manipuler un cercle en 2D, avec un centre et un rayon.
 * 
 * @see Forme
 */
public class Cercle extends Forme {

    private float rayon;

    /**
     * Constructeur de la classe Cercle avec un centre et un rayon spécifiés.
     * 
     * @param centre Le point représentant le centre du cercle.
     * @param rayon La valeur du rayon du cercle.
     */
    Cercle(Point centre, float rayon){
        super(centre);
        this.rayon = rayon;
    }

    /**
     * Constructeur par défaut de la classe Cercle.
     * Ce constructeur initialise un cercle avec un centre à (0, 0) et un rayon de 1.
     */
    Cercle(){
        super();
        this.rayon = 1f;
    }

    /**
     * Retourne le rayon du cercle.
     * 
     * @return Le rayon du cercle.
     */
    public float getRayon(){
        return this.rayon;
    }

    /**
     * Retourne une représentation sous forme de chaîne du cercle.
     * Cette représentation inclut le centre et le rayon du cercle.
     * 
     * @return Une chaîne représentant le cercle avec son centre et son rayon.
     */
    @Override
    public String toString() {
        return super.toString() + ", Rayon (" + this.rayon + ")";
    }

    /**
     * Méthode principale pour tester l'instanciation de cercles avec différents centres et rayons.
     * 
     * 
     */
    public static void main(String[] args) {
        Cercle C1 = new Cercle(new Point(2, 3), 5);
        Cercle C2 = new Cercle();

        System.out.println(C1);
        System.out.println(C2);
    }
}

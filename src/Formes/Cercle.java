package Formes;

/**
 * Classe décrivant un cercle
 */
public class Cercle extends Forme {

    private float rayon;

    /**
     * Getter de rayon
     * @return valeur du rayon
     */
    public float getRayon(){ return this.rayon; }

    Cercle(Point centre, float rayon){
        super(centre);
        this.rayon = rayon;
    }

    Cercle(){
        super();
        this.rayon = 1f;
    }

    public static void main(String[] args) {
        Cercle C1 = new Cercle(new Point(2, 3), 5);
        Cercle C2 = new Cercle();

        System.out.println(C1.getCentre() + " r=" + C1.getRayon());
        System.out.println(C2.getCentre() + " r=" + C2.getRayon());
    }

}

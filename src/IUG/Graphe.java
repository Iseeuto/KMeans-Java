package IUG;

import Formes.Point;
import Kmeans.Groupe;
import Kmeans.KMean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.util.*;
import java.util.List;

/**
 * Classe représentant un graphique affichant des points et intégrant l'algorithme K-means.
 *
 * @param <K> Une instance de KMean représentant l'algorithme utilisé pour le clustering.
 */
public class Graphe<K extends KMean<?>> extends JPanel {

    /**
     * Instance de l'algorithme K-Means.
     */
    public K Kmean;

    /**
     * Taille des points affichés sur le graphique.
     */
    private final int taillePoint = 6;

    /**
     * Rayon de la zone de survol autour d'un point.
     */
    private final int hoverRadius = 5;

    /**
     * Index du point actuellement survolé (-1 si aucun).
     */
    private Formes.Point hovered;

    /**
     * Décalage pour le positionnement des points dans la fenêtre.
     */
    private final int xOffset = 25, yOffset = 25;

    /**
     * Liste des points affichés sur le graphique.
     */
    private HashSet<Formes.Point> Points = new HashSet<>();

    /**
     * Contient une map <etape, centres> avec etape le numero de l'étape correspondante  à
     * centres, une liste des centres à l'étape donnée.
     */
    private HashMap<Integer, HashSet<Formes.Point>> Etapes = new HashMap<>();

    private int etapeActuelle = 0;

    public void suivant(){
        if(this.Etapes.get(etapeActuelle+1) == null){
            if(this.Kmean.calculer()){
                this.etapeActuelle++;
                this.Etapes.put(this.etapeActuelle, (HashSet<Point>) this.Kmean.centres.clone());
            }
        } else {
            this.etapeActuelle++;
        }
        this.repaint();
    }

    public void precedent(){
        if(this.etapeActuelle > 0) this.etapeActuelle--;
        this.repaint();
        return;
    }

    /**
     * Retourne les points extrêmes du graphique.
     * Ces points représentent les valeurs maximales et minimales en X et Y.
     *
     * @return Un tableau contenant deux points : { (xMax, yMax), (xMin, yMin) }.
     */
    private Formes.Point[] getExtremes() {
        float xMax = Float.MIN_VALUE, yMax = Float.MIN_VALUE, xMin = Float.MAX_VALUE, yMin = Float.MAX_VALUE;

        for (Formes.Point p : Points) {
            xMax = Math.max(p.getX(), xMax);
            yMax = Math.max(p.getY(), yMax);
            xMin = Math.min(p.getX(), xMin);
            yMin = Math.min(p.getY(), yMin);
        }

        return new Formes.Point[]{new Formes.Point(xMax, yMax), new Formes.Point(xMin, yMin)};
    }

    /**
     * Vérifie si un point est actuellement survolé par la souris.
     *
     * @param mouseX Position X de la souris.
     * @param mouseY Position Y de la souris.
     * @param p      Point à vérifier.
     * @return {@code true} si le point est survolé, sinon {@code false}.
     */
    private boolean pointEstSurvole(int mouseX, int mouseY, Formes.Point p) {
        int rayon = this.taillePoint / 2 + this.hoverRadius;
        Formes.Point[] extremes = this.getExtremes();
        float xMax = extremes[0].getX(), yMax = extremes[0].getY();
        int posX = (int) (p.getX() / ((double) xMax / (double) (this.getWidth() - xOffset)));
        int posY = (int) (p.getY() / ((double) yMax / (double) (this.getHeight() - yOffset)));

        return (mouseX >= posX - rayon && mouseX <= posX + rayon &&
                mouseY >= posY - rayon && mouseY <= posY + rayon);
    }

    private void dessinerCentres(Graphics g, float xMax, float yMax){
        HashSet<Formes.Point> centres = this.Etapes.get(this.etapeActuelle);
        for(Formes.Point p : centres){
            Groupe groupe = p.groupe;

            float majeur = Float.MIN_VALUE, mineur = Float.MIN_VALUE;
            float dist = Float.MIN_VALUE;

            int posX = (int) (p.getX() / ( xMax / (double) (this.getWidth() - xOffset)));
            int posY = (int) (p.getY() / ( yMax / (double) (this.getHeight() - yOffset)));

            for(Formes.Point _p : p.groupe.points){

                int _posX = (int) (_p.getX() / ( xMax / (double) (this.getWidth() - xOffset)));
                int _posY = (int) (_p.getY() / ( yMax / (double) (this.getHeight() - yOffset)));

                majeur = Float.max(majeur, Math.abs(posX-_posX));
                mineur = Float.max(mineur, Math.abs(posY-_posY));
                dist = Float.max(dist, Point.distanceEuclidienne(new Point(posX, posY), new Point(_posX, _posY)));
            }

            majeur *= 2;
            mineur *= 2;
            dist *= 2;


            g.setColor(groupe.couleur);
            g.fillOval(posX-taillePoint/2, posY-this.taillePoint, this.taillePoint, this.taillePoint);
            //g.drawOval(posX-Math.round(majeur/2), posY-Math.round(mineur/2), Math.round(majeur), Math.round(mineur));
            g.drawArc(posX-Math.round(dist/2), posY-Math.round(dist/2), Math.round(dist), Math.round(dist), 0,360);
        }
    }

    /**
     * Méthode servant à dessiner les points sur le graphique.
     */
    private void dessinerPoints(Graphics g, float xMax, float yMax) {
        int posX;
        int posY;
        for (Formes.Point p : Points) {

            posX = (int) (p.getX() / ((double) xMax / (double) (this.getWidth() - xOffset)));
            posY = (int) (p.getY() / ((double) yMax / (double) (this.getHeight() - yOffset)));

            // Change la couleur du point si celui-ci est survolé
            if (p == this.hovered) {
                g.setColor(Color.WHITE);
            } else {
                if(p.groupe == null) g.setColor(Color.BLACK);
                else g.setColor(p.groupe.couleur);
            }

            g.fillOval(posX-taillePoint/2, posY-taillePoint/2, this.taillePoint, this.taillePoint);
        }
    }

    private void dessinerInformations(Graphics g, float xMax, float yMax){
        int posX = (int) (this.hovered.getX() / ((double) xMax / (double) (this.getWidth() - xOffset)));
        int posY = (int) (this.hovered.getY() / ((double) yMax / (double) (this.getHeight() - yOffset)));
        String text = hovered.getX() + " / " + hovered.getY();
        Font f = new Font("Arial", Font.BOLD, 12);
        g.setFont(f);
        FontRenderContext frc = g.getFontMetrics().getFontRenderContext();
        int textWidth = (int)f.getStringBounds(text, frc).getWidth();
        LineMetrics lm = f.getLineMetrics(text, frc);
        int textHeight = (int)(lm.getAscent() + lm.getDescent());
        g.setColor(Color.WHITE);
        g.fillRect(posX, posY-textHeight+2, textWidth, textHeight);
        g.setColor(Color.BLACK);
        g.drawString(text, posX, posY);
    }

    /**
     * Redéfinit la méthode {@code paintComponent} pour dessiner les points sur le graphique.
     *
     * @param g Composant graphique utilisé pour le rendu.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Formes.Point[] extremes = this.getExtremes();
        float xMax = extremes[0].getX(), yMax = extremes[0].getY();

        // Déssiner les points
        dessinerPoints(g, xMax, yMax);

        if(etapeActuelle > 0) dessinerCentres(g, xMax, yMax);

        // Déssiner les informations du point survolé à la fin
        if(this.hovered != null){ dessinerInformations(g, xMax, yMax); }
    }

    /**
     * Constructeur de la classe {@code Graphe}.
     *
     * @param kmean Instance de l'algorithme K-Means utilisée pour le clustering.
     */
    Graphe(K kmean) {
        this.Kmean = kmean;

//        // Génération aléatoire de points pour le test
//        for (int i = 0; i < Math.random() * 500; i++) {
//            this.Points.add(new Point((int) (Math.random() * 800), (int) (Math.random() * 600)));
//        }

        this.Points = (HashSet<Formes.Point>) this.Kmean.elts;
        this.Etapes.put(this.etapeActuelle, (HashSet<Point>) this.Kmean.centres.clone());

        setBackground(Color.LIGHT_GRAY);

        // Ajoute un écouteur pour détecter le survol des points
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                hovered = null;
                for (Formes.Point p: Points) {
                    if (pointEstSurvole(mouseX, mouseY, p)) {
                        hovered = p;
                        break;
                    }
                }

                if (hovered != null) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

                repaint(); // Redessiner l'affichage
            }
        });
    }
}

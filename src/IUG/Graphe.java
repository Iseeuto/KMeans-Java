package IUG;

import Kmeans.KMean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Graphe<K extends KMean<?>> extends JPanel {

    public K Kmean;
    public Class<?> type;

    public int taillePoint = 6;

    private final int hoverRadius = 5;
    private int indexHover = -1; // Index de l'élément survolé (-1 si aucun)

    private final int xOffset = 25, yOffset = 25;

    private List<Point> Points = new ArrayList<>();

    // return {(xMax, yMax), (yMax, yMin)}
    private Point[] getExtremes(){
        int xMax = Points.get(0).x, yMax =  Points.get(0).y, xMin =  Points.get(0).x, yMin =  Points.get(0).y;

        for(Point p: Points){
            xMax = (p.x > xMax) ? p.x : xMax;
            yMax = (p.y > yMax) ? p.y : yMax;
            xMin = (p.x < xMin) ? p.x : xMin;
            yMin = (p.y < yMin) ? p.y : yMin;
        }

        Point[] extremes = {new Point(xMax, yMax), new Point(xMin, yMin)};
        return extremes;
    }

    private boolean pointEstSurvole(int mouseX, int mouseY, Point p){
        int rayon = this.taillePoint / 2 + this.hoverRadius; // Rayon du point pour mieux détecter le hover
        Point[] extremes = this.getExtremes();
        int xMax = extremes[0].x, yMax = extremes[0].y;
        int posX = (int)(p.x / ((double)xMax/(double)(this.getWidth()-xOffset)));
        int posY = (int)(p.y / ((double)yMax/(double)(this.getHeight()-yOffset)));
        return (mouseX >= posX - rayon && mouseX <= posX + rayon &&
                mouseY >= posY - rayon && mouseY <= posY + rayon);
    }

    private void dessinerPoints(){
        return;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Point[] extremes = this.getExtremes();
        int xMax = extremes[0].x, yMax = extremes[0].y;

        for (int i = 0; i < Points.size(); i++) {

            // Repositionner en fonction de la taille de la fenêtre
            int posX = Points.get(i).x, posY = Points.get(i).y;

            posX = (int)(Points.get(i).x / ((double)xMax/(double)(this.getWidth()-xOffset)));
            posY = (int)(Points.get(i).y / ((double)yMax/(double)(this.getHeight()-yOffset)));


            // Point selectionné
            if(i == this.indexHover){ g.setColor(Color.WHITE);
                // Autres
            }else { g.setColor(Color.BLACK); }
            g.fillOval(posX, posY, this.taillePoint, this.taillePoint);
        }
    }

    Graphe(K kmean){
        this.Kmean = kmean;
        // Pour test seulement
        for(int i=0; i<Math.random()*500; i++){
            this.Points.add(new Point((int)(Math.random()*800), (int)(Math.random()*600)));
        }
        //
        setBackground(Color.LIGHT_GRAY);

        // Détecter hover d'un point
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                indexHover = -1;
                for (int i = 0; i < Points.size(); i++) {
                    if (pointEstSurvole(mouseX, mouseY, Points.get(i))) {
                        indexHover = i;
                        break;
                    }
                }

                if (indexHover != -1) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

                repaint(); // Redessiner
            }
        });

    }
}

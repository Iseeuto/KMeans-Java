package IUG;

import Kmeans.KMean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Graph<K extends KMean<?>> extends JPanel {

    public K Kmean;
    public Class<?> type;

    private Point[] Points = {
            new Point((int)(Math.random()*800), (int)(Math.random()*600)),
            new Point((int)(Math.random()*800), (int)(Math.random()*600)),
            new Point((int)(Math.random()*800), (int)(Math.random()*600)),
            new Point((int)(Math.random()*800), (int)(Math.random()*600)),
            new Point((int)(Math.random()*800), (int)(Math.random()*600)),
            new Point((int)(Math.random()*800), (int)(Math.random()*600)),
            new Point((int)(Math.random()*800), (int)(Math.random()*600)),
            new Point((int)(Math.random()*800), (int)(Math.random()*600)),
            new Point((int)(Math.random()*800), (int)(Math.random()*600)),
            new Point((int)(Math.random()*800), (int)(Math.random()*600)),
    };

    int taillePoint = 8;

    int hoverRadius = 5;
    int indexHover = -1; // Index de l'élément survolé (-1 si aucun)

    private boolean pointEstSurvole(int mouseX, int mouseY, double pointX, double pointY){
        int rayon = this.taillePoint / 2 + this.hoverRadius; // Rayon du point pour mieux détecter le hover
        return (mouseX >= pointX - rayon && mouseX <= pointX + rayon &&
                mouseY >= pointY - rayon && mouseY <= pointY + rayon);
    }

    private void dessinerPoints(){
        return;
    }

    private void dessinerFormes(){
        return;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < Points.length; i++) {
            if(i == this.indexHover){
                g.setColor(Color.WHITE); // Couleur des points
                g.fillOval(this.Points[i].x, this.Points[i].y, this.taillePoint, this.taillePoint);
            } else {
                g.setColor(Color.BLACK); // Couleur des points
                g.fillOval(this.Points[i].x, this.Points[i].y, this.taillePoint, this.taillePoint);
            }
        }
    }

    Graph(K kmean){
        this.Kmean = kmean;
        this.type = this.Kmean.getType();
        setBackground(Color.LIGHT_GRAY);

        // Détecter hover d'un point
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                indexHover = -1;
                for (int i = 0; i < Points.length; i++) {
                    if (pointEstSurvole(mouseX, mouseY, Points[i].getX(), Points[i].getY())) {
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

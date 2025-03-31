package Kmeans;

import Formes.Point;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;

public class Groupe {

    public HashSet<Point> points = new HashSet<>();

    public static final Color[] COLORS = {
            new Color(255,0,0),
            new Color(0,255,0),
            new Color(0,0,255)
    };

    public Color couleur;

    public Groupe(){
        Random rand = new Random();
        this.couleur = COLORS[rand.nextInt(COLORS.length)];
    }

}

package Kmeans;

import Formes.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Groupe {

    public ArrayList<Point> points = new ArrayList<>();

    public Color couleur;

    @Override
    public String toString(){
        String str = "";
        for(Point p: this.points) str += p.toString() + "\n";
        return str;
    }

    public Groupe(){
        Random rand = new Random();
        this.couleur = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
    }

}

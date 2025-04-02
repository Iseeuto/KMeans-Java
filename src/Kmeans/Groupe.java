package Kmeans;

import Formes.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Classe representant un groupe de points dans un algorithme de clustering K-Means.
 * Chaque groupe est associé à une couleur unique et contient une liste de points.
 */
public class Groupe {

    /**
     * Liste des points appartenant à ce groupe.
     */
    public ArrayList<Point> points = new ArrayList<>();

    /**
     * Couleur associée au groupe pour la visualisation.
     */
    public Color couleur;

    /**
     * Constructeur de la classe {@code Groupe}.
     * Initialise une couleur aléatoire pour le groupe.
     */
    public Groupe() {
        Random rand = new Random();
        this.couleur = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
    }

    /**
     * Retourne une représentation textuelle du groupe en listant ses points.
     *
     * @return Une chaîne de caractères contenant les coordonnées des points du groupe.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Point p : this.points) {
            str.append(p.toString()).append("\n");
        }
        return str.toString();
    }
}
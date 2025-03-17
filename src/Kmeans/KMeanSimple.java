package Kmeans;

import Formes.Point;

import java.util.HashSet;

public class KMeanSimple extends KMean<Point>{

    @Override
    protected void initialiserCentres(){ return; }

    @Override
    protected boolean centresEgaux(){ return false; }
    
    @Override
    protected void calculer(){ return; }

    public KMeanSimple(int k, HashSet<Point> elements){
        super(k, elements);
    }

    public KMeanSimple(){
        super(2, new HashSet<Point>());
    }

}

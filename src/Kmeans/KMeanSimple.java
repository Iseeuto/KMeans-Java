package Kmeans;

import Formes.Point;

public class KMeanSimple extends KMean<Point>{

    @Override
    protected void initialiserCentres(){ return; }

    @Override
    protected boolean centresEgaux(){ return false; }
    
    @Override
    protected void calculer(){ return; }

}

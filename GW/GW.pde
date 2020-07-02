int defaultN = 15;
ArrayList<Point> points; 
ArrayList<Point> vertex; //凸包の頂点がここに入る(s.t. {p0, p1,...pr,...p0})

Boolean showPoints = true;

void setup(){
    size(700, 400);
    init();
    smooth();
    cursor(CROSS);
}

void draw(){
    background(240);
    updatePoints();
    updateVertex();
    show();
}

void keyPressed(){
    switch(key){
        case '1':
            showPoints = !showPoints;                
            break;	
    }
}

//----------

void init(){
    points = new ArrayList<Point>();
    for(int i=0; i<defaultN; i++){
        points.add(new Point(i, random(width*0.3, width*0.7), random(height*0.2, height*0.8)));
    }
}

void show(){
    if(showPoints){
        for(Point p: points){
            p.show_self();
        }
    }

    noFill();
    strokeWeight(1);
    stroke(100);  
    beginShape();
    for(Point v: vertex){
        vertex(v.x, v.y);
    }
    endShape();
}

void updatePoints(){
    for(Point p: points){
        p.move();
    }
}

void updateVertex(){
    
    vertex = new ArrayList<Point>();

    //凸包の始点は左端の点とする。
    Point firstP = points.get(0);
    for(Point p: points){
        if(firstP.x > p.x){
            firstP = p;
        }
    }
    firstP._f = true;
    vertex.add(firstP);

    //再び始点に帰るまで，「二点間の直線の片側にのみ他の点が存在する」を満たす点を探し，凸包の頂点とする。
    //上記は判定したい点へのベクトルと，それ以外の点へのベクトルの外積が正か負かで判断できる。
    Boolean finishCircle = false;
    Point capP = firstP;

    while(!finishCircle){
        Boolean isConvex = true;
        Point nextP;

        for(Point p: points){
            if(p == capP) continue;
            Point tryP =  p.sub(capP); //これと，
            isConvex = true;

            for(Point q: points){
                if(q == p || q == capP) continue;
                Point otherP = q.sub(capP); //これを比較。capから任意のotherに対して常に外積が正となるtryが頂点である。
                if(tryP.x*otherP.y - tryP.y*otherP.x < 0) isConvex = false;
            }
            if(isConvex){
                capP = p;
                p._f = true;
                vertex.add(p);
                
                break;
            }
        }

        if(capP == firstP) finishCircle = true;

    }

}
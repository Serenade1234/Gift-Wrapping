import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class GW extends PApplet {

int defaultN = 15;
ArrayList<Point> points; 
ArrayList<Point> vertex; //凸包の頂点がここに入る(s.t. {p0, p1,...pr,...p0})

Boolean showPoints = true;

public void setup(){
    
    init();
    
    cursor(CROSS);
}

public void draw(){
    background(240);
    updatePoints();
    updateVertex();
    show();
}

public void keyPressed(){
    switch(key){
        case '1':
            showPoints = !showPoints;                
            break;	
    }
}

//----------

public void init(){
    points = new ArrayList<Point>();
    for(int i=0; i<defaultN; i++){
        points.add(new Point(i, random(width*0.3f, width*0.7f), random(height*0.2f, height*0.8f)));
    }
}

public void show(){
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

public void updatePoints(){
    for(Point p: points){
        p.move();
    }
}

public void updateVertex(){
    
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
class Point{
    int id;

    float originX, originY;

    float x, y;

    float r = random(height/2, height);

    float rxNoise = random(12345);
    float ryNoise = random(23456);
    float cosAngNoise = random(45683);
    float sinAngNoise = random(75435);

    Boolean _f;

    Point(int _i, float _x, float _y){
        id = _i;
        originX = _x;
        originY = _y;
        x = _x;
        y = _y;

        _f = false;
    }

    public void move(){
        _f = false;
        x = originX + (r*noise(rxNoise)-r/2)*cos(5*TWO_PI*(noise(cosAngNoise)));
        y = originY + (r*noise(ryNoise)-r/2)*sin(5*TWO_PI*(noise(sinAngNoise)));
        rxNoise += 0.007f;
        ryNoise += 0.007f;
        cosAngNoise += 0.001f;
        sinAngNoise += 0.001f;
    }

    public void show_self(){
        strokeWeight(8);
        stroke(50);

        if(_f) stroke(255,0,0);
        point(x, y);
        textSize(30);
        fill(120, 50);
        //text(id, x, y);
    }

    public Point sub(Point p){
        return new Point(-1, this.x-p.x, this.y-p.y);
    }
}
  public void settings() {  size(700, 400);  smooth(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "GW" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}

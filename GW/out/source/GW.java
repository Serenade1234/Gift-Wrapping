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

int defaultN = 6;
ArrayList<Point> points;
ArrayList<Point> vertex;

public void setup(){
    
    init();
    draw_();
}

public void draw_(){
    background(240);
    show();
    //println(to2pi(PI));
    update();
    //println(vertex);
}

public void mousePressed(){
    init();
    draw_();
}

//----------

public void init(){
    points = new ArrayList<Point>();
    for(int i=0; i<defaultN; i++){
        points.add(new Point(i, random(width*0.2f, width*0.8f), random(height*0.2f, height*0.8f)));
    }
}

public void show(){
    for(Point p: points){
        p.show_self();
    }
}

public void update(){
    
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

    Boolean finishCircle = false;
    float lastAngle = PI / 2;
    Point lastP = firstP;
    Point nextP = new Point(-1,0,0);

    int thisId = -1;

    while(!finishCircle){
        //println(finishCircle);
        float maxAngle = - 999; //とりあえずおそろしく小さい値

        for(Point p: points){
            if(p.id == lastP.id) continue;
            float thisAngle = to2pi(atan2(p.y - lastP.y, p.x - lastP.x) - lastAngle);
            if(maxAngle < thisAngle){
                maxAngle = thisAngle;
                nextP = p;
            }
        }
        println(lastP.id, nextP.id);
        println(firstP == nextP);
        //println(to2pi(atan2(nextP.y - lastP.y, nextP.x - lastP.x)) - lastAngle);

        vertex.add(nextP);
        if(nextP.id == firstP.id){
            finishCircle = true;
        }else{
            lastAngle = to2pi(atan2(nextP.y - lastP.y, nextP.x - lastP.x));
            println(lastAngle);
            lastP = nextP;
        }
    }

}

public float to2pi(float ang){
    if(ang >= 0){
        return ang;
    }else{
        return TWO_PI + ang;
    }
}
class Point{
    int id;

    float x;
    float y;

    Boolean _f;

    Point(int _i, float _x, float _y){
        id = _i;
        x = _x;
        y = _y;
        _f = false;
    }

    public void show_self(){
        strokeWeight(5);
        stroke(20);

        if(_f) stroke(255,0,0);
        point(x, y);
        textSize(50);
        text(id, x, y);


    }
}
  public void settings() {  size(1000, 1000); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "GW" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}

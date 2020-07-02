int defaultN = 6;
ArrayList<Point> points;
ArrayList<Point> vertex;

void setup(){
    size(1000, 1000);
    init();
    draw_();
}

void draw_(){
    background(240);
    show();
    //println(to2pi(PI));
    update();
    //println(vertex);
}

void mousePressed(){
    init();
    draw_();
}

//----------

void init(){
    points = new ArrayList<Point>();
    for(int i=0; i<defaultN; i++){
        points.add(new Point(i, random(width*0.2, width*0.8), random(height*0.2, height*0.8)));
    }
}

void show(){
    for(Point p: points){
        p.show_self();
    }
}

void update(){
    
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

float to2pi(float ang){
    if(ang >= 0){
        return ang;
    }else{
        return TWO_PI + ang;
    }
}
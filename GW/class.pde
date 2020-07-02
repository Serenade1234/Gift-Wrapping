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

    void move(){
        _f = false;
        x = originX + (r*noise(rxNoise)-r/2)*cos(5*TWO_PI*(noise(cosAngNoise)));
        y = originY + (r*noise(ryNoise)-r/2)*sin(5*TWO_PI*(noise(sinAngNoise)));
        rxNoise += 0.007;
        ryNoise += 0.007;
        cosAngNoise += 0.001;
        sinAngNoise += 0.001;
    }

    void show_self(){
        strokeWeight(8);
        stroke(50);

        if(_f) stroke(255,0,0);
        point(x, y);
        textSize(30);
        fill(120, 50);
        //text(id, x, y);
    }

    Point sub(Point p){
        return new Point(-1, this.x-p.x, this.y-p.y);
    }
}
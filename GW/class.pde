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

    void show_self(){
        strokeWeight(5);
        stroke(20);

        if(_f) stroke(255,0,0);
        point(x, y);
        textSize(50);
        text(id, x, y);


    }
}
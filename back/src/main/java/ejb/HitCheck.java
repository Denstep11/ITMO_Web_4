package ejb;

import javax.ejb.Stateless;

@Stateless
public class HitCheck {
    Float x;
    Float y;
    Float r;

    public HitCheck(){

    }

    public Boolean result(float x, float y, float r){
        this.x=x;
        this.y=y;
        this.r=r;
        return coordinateQuarterThird()||coordinateQuarterSecond()||coordinateQuarterFirst();
    }

    private Boolean coordinateQuarterThird(){
        return x<=0 && y<=0 && y>=-x-0.5*r;
    }

    private Boolean coordinateQuarterSecond(){
        return x<=0 && y>=0 && x>=-r && y<=r;
    }

    private Boolean coordinateQuarterFirst(){
        return x>=0 && y>=0 && x*x+y*y<=r*r;
    }

}

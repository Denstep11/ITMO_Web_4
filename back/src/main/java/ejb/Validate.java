package ejb;

import javax.ejb.Stateless;

@Stateless
public class Validate {

    private Boolean validateX(Float x){
        for(float i=-4; i<=4;i++){
            if(x==i){
                return true;
            }
        }
        return false;
    }

    private Boolean validateY(Float y){
        String regex = "^-?[0-9]+([.,]?[0-9]+)?$";
        return y.toString().matches(regex) && y>=-5 && y<=5;
    }

    private Boolean validateR(Float r){
        for(float i=-4; i<=4;i++){
            if(r==i){
                return true;
            }
        }
        return false;
    }

    public boolean check(Float x, Float y, Float r){
        return validateX(x) && validateY(y) && validateR(r);
    }

}

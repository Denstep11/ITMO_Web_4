
import Util.HashMD;
import Util.KeyJWT;
import com.google.gson.Gson;
import data.UserDB;
import database.UserDAO;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.Objects;

@Path("/login")
public class LoginController {

    @EJB
    KeyJWT keyJWT;

    @EJB
    UserDAO userDAO;

    @EJB
    HashMD hashMD;

    @GET
    public boolean checkToken(@HeaderParam("token") String token){
        System.out.println(keyJWT.checkJWT(token));
        return keyJWT.checkJWT(token);
    }


    @POST
    public String reqLogin(String req){
        Gson gson = new Gson();


        UserDB user = gson.fromJson(req, UserDB.class);
       // System.out.println(user.getLogin());
       // System.out.println(keyJWT.getKey(user.getLogin()));

        if(userDAO.isfindUserByLogin(user.getLogin())&& Objects.equals(userDAO.findUserByLogin(user.getLogin()).getPassword(), hashMD.run(user.getPassword()))){
            System.out.println(keyJWT.checkJWT(keyJWT.getKey(user.getLogin())));
            return "{\"login\": \""+user.getLogin()+"\", \"token\": \""+keyJWT.getKey(user.getLogin())+"\"}";
        }

        return "{\"token\": \"error\"}";
    }

}

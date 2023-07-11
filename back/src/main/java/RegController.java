import Util.HashMD;
import Util.KeyJWT;
import com.google.gson.Gson;
import data.UserDB;
import database.UserDAO;

import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.Objects;

@Path("/reg")
public class RegController {

    @EJB
    UserDAO userDAO;

    @EJB
    HashMD hashMD;

    @EJB
    KeyJWT keyJWT;

    @POST
    public String reqRegistration(String req){
        Gson gson = new Gson();

        UserDB user = gson.fromJson(req, UserDB.class);
        user.setPassword(hashMD.run(user.getPassword()));

       // System.out.println(userDAO.isfindUserByLogin(user.getLogin()));

        if(!userDAO.isfindUserByLogin(user.getLogin())&& !Objects.equals(user.getLogin(), "")){
            userDAO.save(user);
            return "{\"login\": \""+user.getLogin()+"\", \"token\": \""+keyJWT.getKey(user.getLogin())+"\"}";
        }

        return "{\"token\": \"error\"}";
    }
}

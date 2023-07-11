import Util.KeyJWT;
import com.google.gson.Gson;
import data.CoordinatePoint;
import data.Point;
import data.PointDB;
import database.PointDAO;
import ejb.HitCheck;
import ejb.Validate;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("/points")
public class PointController {

    @EJB
    private HitCheck hitCheck;

    @EJB
    private Validate validate;

    @EJB
    PointDAO pointDAO;

    @EJB
    KeyJWT keyJWT;

    @GET
    public String getReq(@HeaderParam("token") String token){

        Gson gson = new Gson();
        String login = keyJWT.getLogin(token);
        //System.out.println(pointDAO.getAll());

        return gson.toJson(pointDAO.findPointsByLogin(login));
    }

    @POST
    public String getPoints(@HeaderParam("token") String token,@HeaderParam("canvas") boolean isCanvasPoint, String req){

        String login = keyJWT.getLogin(token);
        Date start_time = new Date();
        long start = System.nanoTime();
        Gson gson = new Gson();
        PointDAO pointDAO = new PointDAO();

        CoordinatePoint coordinatePoint = gson.fromJson(req, CoordinatePoint.class);

        if(!validate.check(coordinatePoint.getX(), coordinatePoint.getY(), coordinatePoint.getR())&&!isCanvasPoint){
            return "{\"result\": \"error\"}";
        }

        PointDB pointDB = new PointDB();
        pointDB.setX(coordinatePoint.getX());
        pointDB.setY(coordinatePoint.getY());
        pointDB.setR(coordinatePoint.getR());
        pointDB.setTime(start_time.toString());
        pointDB.setLong_time((System.nanoTime()-start));
        pointDB.setResult(hitCheck.result(coordinatePoint.getX(),coordinatePoint.getY(),coordinatePoint.getR()));
        pointDB.setLogin(login);
        pointDAO.save(pointDB);

        return gson.toJson(pointDB);
    }
}

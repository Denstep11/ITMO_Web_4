package data;

import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class TokenCollection {
    public static List<Point> collection = new ArrayList<>();
}

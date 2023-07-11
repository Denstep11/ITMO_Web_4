package data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="pointsValue")
public class PointDB implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float x;
    private float y;

    private float r;

    @Column(name = "results")
    private boolean result;

    @Column(name = "date")
    private String time;

    @Column(name = "timer")
    private long long_time;

    private String login;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getLong_time() {
        return long_time;
    }

    public void setLong_time(long long_time) {
        this.long_time = long_time;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "PointDB{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", result=" + result +
                ", time='" + time + '\'' +
                ", long_time=" + long_time +
                ", login='" + login + '\'' +
                '}';
    }
}

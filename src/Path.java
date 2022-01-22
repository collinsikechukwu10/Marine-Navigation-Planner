import java.util.List;

public class Path {
    List<Coord> coords;
    float cost;


    Path(List<Coord> coords, float cost) {
        this.coords = coords;
        this.cost = cost;
    }

    public Coord getState() {
        return coords.get(coords.size() - 1);
    }

    public static Path None(){
        return new Path(List.of(),0);
    }

    public List<Coord> getCoords() {
        return coords;
    }

    public float getCost() {
        return cost;
    }

    public boolean isEmpty() {
        return this.coords.size() == 0;
    }
}

import java.util.TreeSet;

public class Vertex implements Comparable<Vertex>,SVGConvertible {
    private double x, y;
    private int id;
    private boolean selected = false;
    private int radius = 30;
    private TreeSet<Vertex> connections = new TreeSet<>();

    Vertex(double x,double y,int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public int getRadius() {
        return radius;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public boolean isSelected() {
        return selected;
    }

    public double getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public TreeSet<Vertex> getConnections() {
        return connections;
    }

    @Override
    public int compareTo(Vertex o) {
        return id - o.getId();
    }

    public void select() {
        selected = true;
    }

    public boolean isConnectedWith(Vertex vertex) {
        return connections.contains(vertex);
    }

    public void addConnection(Vertex vertex) {
        connections.add(vertex);
        connections.addAll(vertex.connections);
    }

    @Override
    public String toSVG() {
        StringBuilder result = new StringBuilder();
        result.append("<circle r=\"");
        result.append(radius);
        result.append("\" cx=\"");
        result.append(x);
        result.append("\" cy=\"");
        result.append(y);
        if (!selected) result.append("\" fill=\"yellow\"/>");
        else result.append("\" fill=\"blue\"/>");
        result.append("\n");
        result.append("<text x=\"");
        result.append(x - 5);
        result.append("\" y=\"");
        result.append(y + 5);
        result.append("\">");
        result.append(id);
        result.append("</text>");
        result.append("\n");
        return result.toString();
    }
}

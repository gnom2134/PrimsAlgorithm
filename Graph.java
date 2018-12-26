import java.util.LinkedList;
import java.util.TreeSet;

public class Graph implements SVGConvertible {
    private LinkedList<Relation> relations = new LinkedList<>();
    private Vertex[] vertices;
    private int centerX, centerY, radius;

    Graph(int x,int y,int r,int n) {
        centerX = x;
        centerY = y;
        radius = r;
        generate(n);
    }

    public LinkedList<Relation> getRelations() {
        return relations;
    }

    public int getCenterY() {
        return centerY;
    }

    public int getArrayPower() {
        return vertices.length;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    private boolean isConnectedWithOthers(Vertex vertex) {
        TreeSet<Vertex> currentVertices = vertex.getConnections();
        for (Vertex v : vertices) {
            if(!(currentVertices.contains(v) || vertex.getId() == v.getId())) return false;
        }
        return true;
    }

    private void generate(int n) {
        double x,y;
        vertices = new Vertex[n];

        for (int i = 0;i < n;i++) {
            x = centerX + radius * Math.cos(i * 2 * Math.PI / n);
            y = centerY + radius * Math.sin(i * 2 * Math.PI / n);
            vertices[i] = new Vertex(x,y,i);
        }

        while (true) {
            int first = (int)(Math.random() * n);
            if(isConnectedWithOthers(vertices[first])) break;
            int second = (int)(Math.random() * n);
            if (!( (first == second) || (vertices[first].isConnectedWith(vertices[second])) ) ) {
                relations.add(new Relation(vertices[first],vertices[second],(int)(Math.random() * 50)));
            }
        }
    }

    @Override
    public String toSVG() {
        StringBuilder result = new StringBuilder();
        for (Relation relation : relations) {
            result.append(relation.toSVG());
        }
        for (Vertex vertex : vertices) {
            result.append(vertex.toSVG());
        }
        return result.toString();
    }
}

public class Relation implements SVGConvertible {
    private Vertex first, second;
    private boolean selected = false;
    private int value;

    Relation(Vertex vertex1,Vertex vertex2,int value) {
        first = vertex1;
        second = vertex2;
        this.value = value;
        vertex1.addConnection(vertex2);
        vertex2.addConnection(vertex1);
    }

    public int getValue() {
        return value;
    }

    public Vertex getFirst() {
        return first;
    }

    public Vertex getSecond() {
        return second;
    }

    public boolean isSelected() {
        return selected;
    }

    public void select() {
        selected = true;
    }

    @Override
    public String toSVG() {
        StringBuilder result = new StringBuilder();
        result.append("<line x1=\"");
        result.append(first.getX());
        result.append("\" y1=\"");
        result.append(first.getY());
        result.append("\" x2=\"");
        result.append(second.getX());
        result.append("\" y2=\"");
        result.append(second.getY());
        if(!selected) result.append("\" stroke=\"black\"/>");
        else result.append("\" stroke=\"blue\"/>");
        result.append("\n");
        double x = first.getX() + (second.getX() - first.getX()) / 3 - 20;
        double y = first.getY() + (second.getY() - first.getY()) / 3 - 10;
        result.append("<text x=\"");
        result.append(x);
        result.append("\" y=\"");
        result.append(y);
        if(selected) result.append("\" fill=\"blue");
        result.append("\">");
        result.append(value);
        result.append("</text>");
        result.append("\n");
        return result.toString();
    }
}

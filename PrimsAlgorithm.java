import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class PrimsAlgorithm implements SVGConvertible {
    private Relation[][] matrix;
    private Graph graph;
    private int[] selectedRows;
    private LinkedList<Relation> changes = new LinkedList<>();

    PrimsAlgorithm(Graph graph) {
        this.graph = graph;
        matrix = new Relation[graph.getArrayPower()][graph.getArrayPower()];
        for (Relation relation : graph.getRelations()) {
            matrix[relation.getFirst().getId()][relation.getSecond().getId()] = relation;
            matrix[relation.getSecond().getId()][relation.getFirst().getId()] = relation;
        }
        selectedRows = new int[graph.getArrayPower()];
        Arrays.fill(selectedRows,0);
        selectedRows[0] = 1;
        while (!isEnd()) {
            nextStep();
        }
    }

    public LinkedList<Relation> getChanges() {
        return changes;
    }

    public Graph getGraph() {
        return graph;
    }

    private boolean isEnd(){
        for (int i = 0; i < selectedRows.length; i++) {
            if (selectedRows[i] == 0) return false;
        }
        return true;
    }

    private void nextStep() {
        Relation temporary = findMin();
        changes.add(temporary);
    }

    private Relation findMin() {
        int minValue = 100;
        int minI = 0, minJ = 0;
        for (int i = 0;i < selectedRows.length;i++) {
            if (selectedRows[i] == 0) continue;
            for (int j = 0;j < selectedRows.length;j++) {
                if (selectedRows[j] == 1 || matrix[i][j] == null) continue;
                if (matrix[i][j].getValue() < minValue) {
                    minValue = matrix[i][j].getValue();
                    minI = i;
                    minJ = j;
                }
            }
        }
        selectedRows[minJ] = 1;
        return matrix[minI][minJ];
    }

    @Override
    public String toSVG() {
        int bias = graph.getCenterY();
        StringBuilder result = new StringBuilder();
        result.append(graph.toSVG());
        Iterator<Relation> relationIterator = changes.iterator();
        while (relationIterator.hasNext()) {
            result.append("<!-- hello -->\n");
            Relation temporary = relationIterator.next();
            temporary.getFirst().select();
            temporary.getSecond().select();
            temporary.select();
            graph.setCenterY(graph.getCenterY() + bias);
            result.append(graph.toSVG());
        }
        return result.toString();
    }
}

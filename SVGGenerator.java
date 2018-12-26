import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class SVGGenerator {
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Select format : \n\t1 - SVG\n\t2 - Window");
        int choice = in.nextInt();
        if(choice == 1) {
            String start = "<html>\n<head></head>\n<body>\n<svg width=\"2000\" height=\"100000\">\n";
            String end = "</svg>\n</body>\n</html>";
            try {
                for (int i = 0; i < 7; i++) {
                    File file = new File("index" + (i + 1) + ".html");
                    FileWriter out = new FileWriter(file);
                    Graph graph = new Graph(800, 900, 320, 6 + 2 * i);
                    PrimsAlgorithm primsAlgorithm = new PrimsAlgorithm(graph);
                    out.write(start + primsAlgorithm.toSVG() + end);
                    System.out.println("Done! " + i);
                    out.flush();
                }
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        } if (choice == 2) {
            TextField textField = new TextField();
            JFrame frame = new JFrame("Prim\'s algorithm");
            frame.setLayout(new BorderLayout());
            frame.setSize(1600, 1800);
            textField.setText("Enter the number of vertices");
            frame.add(textField,BorderLayout.NORTH);
            ActionListener listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(Integer.parseInt(textField.getText()) > 18) textField.setText(Integer.toString(18));
                    if(Integer.parseInt(textField.getText()) < 2) textField.setText(Integer.toString(2));
                    Graph graph = new Graph(350, 350, 320,Integer.parseInt(textField.getText()));
                    PrimsAlgorithm primsAlgorithm = new PrimsAlgorithm(graph);
                    drawGraph(primsAlgorithm);
                }
            };
            JButton button = new JButton("Perform");
            button.addActionListener(listener);
            frame.add(button,BorderLayout.CENTER);
            frame.setVisible(true);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }

    private static void drawGraph(PrimsAlgorithm primsAlgorithm) {
        LinkedList<Relation> changes = primsAlgorithm.getChanges();
        Iterator<Relation> iterator = changes.iterator();
        DrawComponent drawComponent = new DrawComponent(primsAlgorithm);
        JFrame frame = new JFrame("Prim\'s algorithm");
        frame.setLayout(new BorderLayout());
        JButton button = new JButton("Next step");
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(iterator.hasNext()) {
                    Relation temporary = iterator.next();
                    temporary.getFirst().select();
                    temporary.getSecond().select();
                    temporary.select();
                    drawComponent.repaint();
                    if (!iterator.hasNext()) button.hide();
                }
            }
        };
        button.addActionListener(listener);
        frame.add(button,BorderLayout.NORTH);
        frame.add(drawComponent,BorderLayout.CENTER);
        frame.setVisible(true);
        frame.pack();
    }
}

class DrawComponent extends JComponent {
    private static final int DEFAULT_WIDTH = 750;
    private static final int DEFAULT_HEIGHT = 750;
    private PrimsAlgorithm primsAlgorithm;
    private Graphics2D g2;

    DrawComponent (PrimsAlgorithm primsAlgorithm) {
        this.primsAlgorithm = primsAlgorithm;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        this.g2 = (Graphics2D) g;
        g2.setPaint(Color.gray);
        Graph graph = primsAlgorithm.getGraph();
        drawGraph(graph);
    }

    private void drawGraph(Graph graph) {
        LinkedList<Relation> relations = graph.getRelations();
        Vertex[] vertices = graph.getVertices();

        for (Relation r : relations) {
            if (r.isSelected()) g2.setPaint(Color.blue);

            Vertex first = r.getFirst();
            Vertex second = r.getSecond();
            int radius = first.getRadius();
            Line2D line = new Line2D.Double(first.getX() + radius,first.getY() + radius,
                    second.getX() + radius,second.getY() + radius);
            double x = first.getX() + radius + (second.getX() - first.getX()) / 3 - 20;
            double y = first.getY() + radius + (second.getY() - first.getY()) / 3 - 10;

            g2.draw(line);
            g2.drawString(Integer.toString(r.getValue()),(int)x,(int)y);

            g2.setPaint(Color.gray);
        }

        for (Vertex v : vertices) {
            if (v.isSelected()) g2.setPaint(Color.blue);

            int radius = v.getRadius();
            Ellipse2D circle = new Ellipse2D.Double(v.getX(),v.getY(),60,60);
            g2.fill(circle);
            g2.draw(circle);

            g2.setPaint(Color.black);

            g2.drawString(Integer.toString(v.getId()),(int)v.getX() + radius - 5,(int)v.getY() + radius + 5);

            g2.setPaint(Color.gray);
        }
    }
}

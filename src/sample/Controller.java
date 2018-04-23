package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.StrokeType;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    TextField nodesNumber;
    @FXML
    TextField edgeData;
    @FXML
    Pane pane;
    @FXML
    ScrollPane scrollPane;

    int nodes ;
    int[][] edges;
    boolean[][] visited ;
    List paths = new ArrayList();

    public void addArc() {

        String[] data = edgeData.getText().split(" ");
        int initialNode = Integer.parseInt(data[0]);
        int finalNode = Integer.parseInt(data[1]);
        int gain = Integer.parseInt(data[2]);
        edges[initialNode-1][finalNode-1] = gain;
        int distanceBetweenChosenNodes = (finalNode - initialNode);
        double deltaX = 80.0;
        double x = scrollPane.getHeight() / 2 + deltaX * initialNode, y = scrollPane.getHeight() / 2;
        Path path ;
        if (initialNode==finalNode)
        {
            ArcTo arcTo = new ArcTo();
            arcTo.setX(x - 20 + 1); // to simulate a full 360 degree celcius circle.
            arcTo.setY(y - 20);
            arcTo.setSweepFlag(false);
            arcTo.setLargeArcFlag(true);
            arcTo.setRadiusX(20);
            arcTo.setRadiusY(20);
            arcTo.setXAxisRotation(0);

            Path pathTest = PathBuilder.create()
                    .elements(
                            new MoveTo(x - 20, y - 20),
                            arcTo,
                            new ClosePath()) // close 1 px gap.
                    .build();
            path= pathTest;
        }
        else
        {
            path = new Path();
            MoveTo moveTo = new MoveTo();
            moveTo.setX(x);
            moveTo.setY(y);
            ArcTo arcTo = new ArcTo();
            arcTo.setSweepFlag(true);
            arcTo.setX(x + deltaX * distanceBetweenChosenNodes);
            arcTo.setY(y);
            arcTo.setRadiusX(180.0);
            arcTo.setRadiusY(180.0);
            path.getElements().add(moveTo);
            path.getElements().add(arcTo);
            System.out.println(arcTo.getRadiusY());
            System.out.println(arcTo.radiusYProperty());
        }
        pane.getChildren().add(path);
        Label label = new Label(data[2]);
        label.setLayoutX(x + (deltaX * distanceBetweenChosenNodes) / 2);
        label.setLayoutY(180.0);
        pane.getChildren().add(label);
    }

    public void addCircle() {
        pane.getChildren().clear();
        nodes = Integer.parseInt(nodesNumber.getText());
        edges = new int[nodes][nodes];
        visited = new boolean[nodes][nodes];
        double x = scrollPane.getHeight() / 2, y = scrollPane.getHeight() / 2;
        double deltaX = 80.0;
        double radius = 6.0;
        for (int i = 0; i < nodes; i++) {
            Circle circle = new Circle();
            circle.setFill(Color.WHITE);
            circle.setStrokeType(StrokeType.INSIDE);
            circle.setStroke(Color.BLACK);
            circle.setLayoutX(x + deltaX);
            x += deltaX;
            circle.setLayoutY(y);
            circle.setRadius(radius);
            pane.getChildren().add(circle);
        }
    }

    public void solveScene() {
        String[] pathData = new String[2];
        pathData[0]="";
        pathData[1]="";
        forwardPath_dfs(0,1, pathData);

    }


    public void forwardPath_dfs(int row, int col, String[] pathData) {
        int nodesNum = edges.length;

        for (int j = col; j < nodesNum; j++) {
            if (visited[row][j] == false) {
                visit(row, j, pathData);
                if (edges[row][j] == 0)
                    forwardPath_dfs(row,j+1, pathData);
                if(edges[row][j]!=0)
                    forwardPath_dfs(j,j+1, pathData);
            }

        }
    }



    private void unvisit(int row) {
    }

    private void visit(int row, int j, String[] pathData) {
        visited[row][j] = true;
        if (edges[row][j]!=0){
            pathData[0] = pathData[0] + String.valueOf(row);
            pathData[1] = pathData[1] + String.valueOf(edges[row][j]);
        }
    }
}

package sample;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    private static final double WIDTH = 800;
    private static final double HEIGHT = 800;
    private static Pane ROOT_PANE = new Pane();
    private static Pane maze_pane = new Pane();
    private static Pane data_pane = new Pane();
    Text data_message = new Text("");
    private static final int GRIFWIDTH = 20;

    @Override
    public void start(Stage primaryStage) throws Exception{
        _init();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(ROOT_PANE, WIDTH, HEIGHT));
        primaryStage.show();
    }

    /**
     * 画迷宫
     * */
    private void drawMaze(){
        maze_pane.getChildren().clear();
        for (GridModel[] models:Algorithm.getGridModels())
            for (GridModel model:models)
                drawAGrid(model);
    }


    /**
     * 画一个迷宫格子
     * */
    private void drawAGrid(GridModel model){
        //画格子的上边
        if (!model.topIsEmpty()) drawLine(model.getX() * GRIFWIDTH,model.getY() * GRIFWIDTH,(model.getX()+1) * GRIFWIDTH,model.getY() * GRIFWIDTH,"");
        //画格子的右边
        if (!model.rightIsEmpty()) drawLine((model.getX()+1) * GRIFWIDTH,model.getY() * GRIFWIDTH,(model.getX()+1) * GRIFWIDTH,(model.getY() + 1)  * GRIFWIDTH,"");
        //画格子的下边
        if (!model.buttonIsEmpty()) drawLine(model.getX() * GRIFWIDTH,(model.getY() + 1) * GRIFWIDTH,(model.getX()+1) * GRIFWIDTH,(model.getY() + 1) * GRIFWIDTH,"");
        //画格子的左边
        if (!model.leftIsEmpty()) drawLine(model.getX() * GRIFWIDTH,model.getY() * GRIFWIDTH,model.getX() * GRIFWIDTH,(model.getY() + 1) * GRIFWIDTH,"");
    }
    /**
     * 画一条线
     * */
    private void drawLine(int startX,int startY,int endX,int endY,String id){
        Line line = new Line(startX,startY,endX,endY);
        line.setId(id);
        line.setStroke(id.equals("way") ? Color.RED : Color.BLACK);
        maze_pane.getChildren().add(line);
    }

    private void showOperation(){
        data_pane.setLayoutX(WIDTH - 150);
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        HBox hbox = new HBox();
        TextField N = new TextField("10");
        TextField M = new TextField("10");
        N.setPrefWidth(50);
        M.setPrefWidth(50);
        hbox.setSpacing(10);
        hbox.getChildren().addAll(N,M);
        HBox hbox1 = new HBox();
        TextField startX = new TextField("0");
        TextField startY = new TextField("0");
        startX.setPrefWidth(50);
        startY.setPrefWidth(50);
        hbox1.setSpacing(10);
        hbox1.getChildren().addAll(startX,startY);
        HBox hbox2 = new HBox();
        TextField endX = new TextField("9");
        TextField endY = new TextField("9");
        endX.setPrefWidth(50);
        endY.setPrefWidth(50);
        hbox2.setSpacing(10);
        hbox2.getChildren().addAll(endX,endY);
        Button createMaze = new Button("创建迷宫");
        Button isHave = new Button("检测是否有路径");
        Button showMin = new Button("显示最短路径");
        Button allWays = new Button("查看所有路径");
        vBox.getChildren().addAll(hbox,hbox1,hbox2,createMaze,isHave,showMin,allWays);
        data_pane.getChildren().addAll(vBox);
        createMaze.setOnMouseClicked(event -> createMaze(Integer.valueOf(N.getText()),Integer.valueOf(M.getText())));
        isHave.setOnMouseClicked(event -> showIsHaveWay(Integer.valueOf(startX.getText()),Integer.valueOf(startY.getText()),Integer.valueOf(endX.getText()),Integer.valueOf(endY.getText())));
        showMin.setOnMouseClicked(event -> showMinWay(Integer.valueOf(startX.getText()),Integer.valueOf(startY.getText()),Integer.valueOf(endX.getText()),Integer.valueOf(endY.getText()),true));
        allWays.setOnMouseClicked(event ->showAllWays(Integer.valueOf(startX.getText()),Integer.valueOf(startY.getText()),Integer.valueOf(endX.getText()),Integer.valueOf(endY.getText())));
    }

    private void _init(){
        showOperation();
        data_message.setWrappingWidth(130);
        data_message.setY(HEIGHT - 200);
        data_pane.getChildren().add(data_message);
        ROOT_PANE.setLayoutX(10);
        ROOT_PANE.setLayoutY(10);
        ROOT_PANE.getChildren().addAll(maze_pane,data_pane);
    }
    private void showIsHaveWay(int startX,int startY,int endX,int endY){
        boolean isHaveWay = Algorithm.isHaveWay(startX,startY,endX,endY);
        String[] strings;
        if (!isHaveWay){
            showMessage("无解");
            return;
        } else {
            showMessage("有路径");
            Algorithm.findPathways(startX,startY,endX,endY,true,false);
            showMinWay(startX,startY,endX,endY,false);
        }
    }
    private void showAllWays(int startX,int startY,int endX,int endY){
        showMessage("找到了" + (Algorithm.findPathways(startX,startY,endX,endY,false,false).length - 1) + "条路径");
    }
    private void showMinWay(int startX,int startY,int endX,int endY,boolean min){
        clearMinWay();
        if (!Algorithm.isHaveWay(startX,startY,endX,endY)){ showMessage("无法到达指定终点");return;}
        if (min) Algorithm.findPathways(startX,startY,endX,endY,false,true);
        int[][] ways = Algorithm.getWays();
        for (int[] ints:ways) {
            for (int _int : ints)
                System.out.printf("%-4d", _int);
            System.out.println();
        }
        GridModel[][] gridModels = Algorithm.getGridModels();
        GridModel model = gridModels[endY][endX];
        while (true){
            if (model.topIsEmpty() && ways[model.getY() - 1][model.getX()] == ways[model.getY()][model.getX()] - 1){
                drawMidline(model,gridModels[model.getY() - 1][model.getX()]);
                model = gridModels[model.getY() - 1][model.getX()];
            }else if (model.rightIsEmpty() && ways[model.getY()][model.getX() + 1] == ways[model.getY()][model.getX()] - 1){
                drawMidline(model,gridModels[model.getY()][model.getX() + 1]);
                model = gridModels[model.getY()][model.getX() + 1];
            }else if (model.buttonIsEmpty() && ways[model.getY() + 1][model.getX()] == ways[model.getY()][model.getX()] - 1){
                drawMidline(model,gridModels[model.getY() + 1][model.getX()]);
                model = gridModels[model.getY() + 1][model.getX()];
            }else if (model.leftIsEmpty() && ways[model.getY()][model.getX() - 1] == ways[model.getY()][model.getX()] - 1){
                drawMidline(model,gridModels[model.getY()][model.getX() - 1]);
                model = gridModels[model.getY()][model.getX() - 1];
            }else break;
        }
    }
    private void drawMidline(GridModel model,GridModel model1){
        drawLine(model.getX()*GRIFWIDTH + GRIFWIDTH/2,model.getY() * GRIFWIDTH + GRIFWIDTH/2,
                model1.getX()*GRIFWIDTH + GRIFWIDTH/2,model1.getY() * GRIFWIDTH + GRIFWIDTH/2,"way");
    }
    private void showMessage(String message){
        data_message.setText(message);
    }

    private void clearMinWay(){
        List<Node> nodes = new ArrayList<>();
        for (Node node:maze_pane.getChildren()){
            if (node instanceof Line && node.getId().equals("way"))
                nodes.add(node);
        }
        for (Node node:nodes)
            maze_pane.getChildren().remove(node);
    }

    private void createMaze(int N,int M){
        Algorithm.setNM(N,M);
        Algorithm.createMaze();
        drawMaze();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

package sample;


import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by 11723 on 2017/5/29.
 */
public class Algorithm {
    private static int N = 10;
    private static int M = 10;
    private static GridModel[][] gridModels;
    private static int[][] ways;
    private static int startX;
    private static int startY;
    private static int endX;
    private static int endY;
    private static String MESSAGE = "";//记录路径(0,0,1)-(0,1,2) 0 1 2 3 上右下左
    public static void init(){
        gridModels = new GridModel[N][M];
        ways = new int[N][M];
        for (int i = 0;i < gridModels.length;i++)
            for (int j = 0;j < gridModels[i].length;j++)
                gridModels[i][j] = new GridModel();
    }
    public static void setNM(int n,int m){
        N = n;
        M = m;
        init();
    }
    public static void setSartXY(int x,int y){
        startX = x;
        startY = y;
    }
    public static void setEndXY(int x,int y){
        endX = x;
        endY = y;
    }

    public static boolean isHaveWay(int startX,int startY,int endX,int endY){
        MESSAGE = "";
        setSartXY(startX,startY);
        setEndXY(endX,endY);
        maps.clear();
        queue.clear();
        queue.add(gridModels[startX][startY]);
        return BFS();
    }

    /**
     * 找到通路 如果不存在通路返回null
     * */
    public static String[] findPathways(int startX,int startY,int endX,int endY,boolean oneWay,boolean isMin){
        MESSAGE = "";
        MIN = 9999999;
        for (int i = 0;i < ways.length;i++)
            for (int j = 0;j < ways[i].length;j++)
                ways[i][j] = 0;
        setSartXY(startX,startY);
        setEndXY(endX,endY);
        String msg = getWays(oneWay,isMin);
        if (oneWay)
            return msg.split("OK-")[0].split("-");
        else {
            return msg.split("OK-");
        }
    }


    /**
     * 获取所有路径
     * */
    private static String getWays(boolean oneWay,boolean isMIn){
        deep = 0;
        maps.clear();
        stack.clear();
        DFS(oneWay,isMIn);
//        for (int[] ints:ways) {
//            for (int _int : ints)
//                System.out.printf("%4d",_int);
//            System.out.println();
//        }
        return MESSAGE;
    }
    /**
     * 深度遍历所有路径
     * */
    private static int deep = 0;
//    private static String DFS(GridModel model,String content,int inType,int num){
//        String msg = null;
//        if (isHavaGo(model.getX(),model.getY(),content)) {
//            return content;
//        }
//        if (ways[model.getY()][model.getX()] ==0 || ways[model.getY()][model.getX()] > num) ways[model.getY()][model.getX()] = num;
//        else return content;
//        if (model.getX() == endX && model.getY() == endY){
//            MESSAGE += (content + "OK-");
//            System.out.println("找到终点" + deep++ + "次");
//            return content;
//        }
//        if (model.topIsEmpty() && inType != 0){
//            msg = "(" + model.getX() + "," + model.getY() + ",0)-";
//            DFS(gridModels[model.getY() - 1][model.getX()],content + msg,2,num + 1);
//        }
//        if (model.rightIsEmpty() && inType != 1){
//            msg = "(" + model.getX() + "," + model.getY() + ",1)-";
//            DFS(gridModels[model.getY()][model.getX() + 1],content + msg,3,num + 1);
//        }
//        if (model.buttonIsEmpty() && inType != 2){
//            msg = "(" + model.getX() + "," + model.getY() + ",2)-";
//            DFS(gridModels[model.getY() + 1][model.getX()],content + msg,0,num + 1);
//        }
//        if (model.leftIsEmpty() && inType != 3){
//            msg = "(" + model.getX() + "," + model.getY() + ",3)-";
//            DFS(gridModels[model.getY()][model.getX() - 1],content + msg,1,num + 1);
//        }
//        return content;
//    }



    private static Stack<StackModel> stack = new Stack<>();
    private static void DFS(boolean oneWay,boolean isMIn){
        int T = getType(gridModels[startX][startY]);
        String msg = "";
        if (gridModels[startX][startY].BranchNum() >= 2){
            if (gridModels[startX][startY].topIsEmpty()){
                msg = DFS(gridModels[startX][startY],"",2,0,isMIn);
            }else if (gridModels[startX][startY].rightIsEmpty()){
                msg = DFS(gridModels[startX][startY],"",3,0,isMIn);
            }else if (gridModels[startX][startY].buttonIsEmpty()){
                msg = DFS(gridModels[startX][startY],"",0,0,isMIn);
            }else if (gridModels[startX][startY].leftIsEmpty()){
                msg = DFS(gridModels[startX][startY],"",1,0,isMIn);
            }
        }else msg = DFS(gridModels[startX][startY],"",-1,0,isMIn);
        while (true){
            if (msg.matches(".*OK-")){
                MESSAGE += msg;
                if (oneWay) return;
                msg = "<fail>";
            }else if ((msg.matches(".*<stack>") || msg.matches(".*<fail>")) && !stack.isEmpty()){
                msg = chose(isMIn,msg,T);
            }else{
                return;
            }
        }
    }
    private static String chose(boolean isMIn,String msg,int T){
        StackModel model = stack.peek();
        int type = isMIn ? getType(model.getModel()) : T;
//        int type = getType(model.getModel());
        if (type == 0){//上右
            if (model.getModel().topIsEmpty() && !model.getTopHaveGo()){
                stack.peek().setTop();
                return DFS(gridModels[model.getModel().getY() - 1][model.getModel().getX()],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",0)-",
                        2,model.getNum() + 1,isMIn);
            }else if (model.getModel().rightIsEmpty() && !model.getRightHaveGo()){
                stack.peek().setRight();
                return DFS(gridModels[model.getModel().getY()][model.getModel().getX() + 1],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",1)-",
                        3,model.getNum() + 1,isMIn);
            }else if (model.getModel().buttonIsEmpty() && !model.getButtonHaveGo()){
                stack.peek().setButton();
                return DFS(gridModels[model.getModel().getY() + 1][model.getModel().getX()],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",2)-",
                        0,model.getNum() + 1,isMIn);
            }else if (model.getModel().leftIsEmpty() && !model.getLeftHaveGo()){
                stack.peek().setLeft();
                return DFS(gridModels[model.getModel().getY()][model.getModel().getX() - 1],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",3)-",
                        1,model.getNum() + 1,isMIn);
            }else {
                stack.pop();
            }
        }else if (type == 1){
            if (model.getModel().topIsEmpty() && !model.getTopHaveGo()){
                stack.peek().setTop();
                return DFS(gridModels[model.getModel().getY() - 1][model.getModel().getX()],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",0)-",
                        2,model.getNum() + 1,isMIn);
            }else if (model.getModel().leftIsEmpty() && !model.getLeftHaveGo()){
                stack.peek().setLeft();
                return DFS(gridModels[model.getModel().getY()][model.getModel().getX() - 1],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",3)-",
                        1,model.getNum() + 1,isMIn);
            }else if (model.getModel().buttonIsEmpty() && !model.getButtonHaveGo()){
                stack.peek().setButton();
                return DFS(gridModels[model.getModel().getY() + 1][model.getModel().getX()],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",2)-",
                        0,model.getNum() + 1,isMIn);
            }else if (model.getModel().rightIsEmpty() && !model.getRightHaveGo()){
                stack.peek().setRight();
                return DFS(gridModels[model.getModel().getY()][model.getModel().getX() + 1],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",1)-",
                        3,model.getNum() + 1,isMIn);
            }else {
                stack.pop();
            }
        }else if (type == 2){
            if (model.getModel().buttonIsEmpty() && !model.getButtonHaveGo()){
                stack.peek().setButton();
                return DFS(gridModels[model.getModel().getY() + 1][model.getModel().getX()],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",2)-",
                        0,model.getNum() + 1,isMIn);
            } else if (model.getModel().rightIsEmpty() && !model.getRightHaveGo()){
                stack.peek().setRight();
                return DFS(gridModels[model.getModel().getY()][model.getModel().getX() + 1],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",1)-",
                        3,model.getNum() + 1,isMIn);
            } else if (model.getModel().topIsEmpty() && !model.getTopHaveGo()){
                stack.peek().setTop();
                return DFS(gridModels[model.getModel().getY() - 1][model.getModel().getX()],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",0)-",
                        2,model.getNum() + 1,isMIn);
            } else if (model.getModel().leftIsEmpty() && !model.getLeftHaveGo()){
                stack.peek().setLeft();
                return DFS(gridModels[model.getModel().getY()][model.getModel().getX() - 1],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",3)-",
                        1,model.getNum() + 1,isMIn);
            } else {
                stack.pop();
            }
        }else if (type == 3){
            if (model.getModel().buttonIsEmpty() && !model.getButtonHaveGo()){
                stack.peek().setButton();
                return DFS(gridModels[model.getModel().getY() + 1][model.getModel().getX()],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",2)-",
                        0,model.getNum() + 1,isMIn);
            } else if (model.getModel().leftIsEmpty() && !model.getLeftHaveGo()){
                stack.peek().setLeft();
                return DFS(gridModels[model.getModel().getY()][model.getModel().getX() - 1],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",3)-",
                        1,model.getNum() + 1,isMIn);
            } else if (model.getModel().topIsEmpty() && !model.getTopHaveGo()){
                stack.peek().setTop();
                return DFS(gridModels[model.getModel().getY() - 1][model.getModel().getX()],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",0)-",
                        2,model.getNum() + 1,isMIn);
            } else if (model.getModel().rightIsEmpty() && !model.getRightHaveGo()){
                stack.peek().setRight();
                return DFS(gridModels[model.getModel().getY()][model.getModel().getX() + 1],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",1)-",
                        3,model.getNum() + 1,isMIn);
            } else {
                stack.pop();
            }
        }else if (type == 4){
            if (model.getModel().topIsEmpty() && !model.getTopHaveGo()){
                stack.peek().setTop();
                return DFS(gridModels[model.getModel().getY() - 1][model.getModel().getX()],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",0)-",
                        2,model.getNum() + 1,isMIn);
            } else if (model.getModel().rightIsEmpty() && !model.getRightHaveGo()){
                stack.peek().setRight();
                return DFS(gridModels[model.getModel().getY()][model.getModel().getX() + 1],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",1)-",
                        3,model.getNum() + 1,isMIn);
            } else if (model.getModel().leftIsEmpty() && !model.getLeftHaveGo()){
                stack.peek().setLeft();
                return DFS(gridModels[model.getModel().getY()][model.getModel().getX() - 1],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",3)-",
                        1,model.getNum() + 1,isMIn);
            } else if (model.getModel().buttonIsEmpty() && !model.getButtonHaveGo()){
                stack.peek().setButton();
                return DFS(gridModels[model.getModel().getY() + 1][model.getModel().getX()],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",2)-",
                        0,model.getNum() + 1,isMIn);
            }else {
                stack.pop();
            }
        }else if (type == 5){
            if (model.getModel().rightIsEmpty() && !model.getRightHaveGo()){
                stack.peek().setRight();
                return DFS(gridModels[model.getModel().getY()][model.getModel().getX() + 1],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",1)-",
                        3,model.getNum() + 1,isMIn);
            }else if (model.getModel().topIsEmpty() && !model.getTopHaveGo()){
                stack.peek().setTop();
                return DFS(gridModels[model.getModel().getY() - 1][model.getModel().getX()],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",0)-",
                        2,model.getNum() + 1,isMIn);
            } else if (model.getModel().buttonIsEmpty() && !model.getButtonHaveGo()){
                stack.peek().setButton();
                return DFS(gridModels[model.getModel().getY() + 1][model.getModel().getX()],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",2)-",
                        0,model.getNum() + 1,isMIn);
            } else if (model.getModel().leftIsEmpty() && !model.getLeftHaveGo()){
                stack.peek().setLeft();
                return DFS(gridModels[model.getModel().getY()][model.getModel().getX() - 1],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",3)-",
                        1,model.getNum() + 1,isMIn);
            }else {
                stack.pop();
            }
        }else if (type == 6){
            if (model.getModel().buttonIsEmpty() && !model.getButtonHaveGo()){
                stack.peek().setButton();
                return DFS(gridModels[model.getModel().getY() + 1][model.getModel().getX()],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",2)-",
                        0,model.getNum() + 1,isMIn);
            } else if (model.getModel().leftIsEmpty() && !model.getLeftHaveGo()){
                stack.peek().setLeft();
                return DFS(gridModels[model.getModel().getY()][model.getModel().getX() - 1],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",3)-",
                        1,model.getNum() + 1,isMIn);
            }else if (model.getModel().rightIsEmpty() && !model.getRightHaveGo()){
                stack.peek().setRight();
                return DFS(gridModels[model.getModel().getY()][model.getModel().getX() + 1],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",1)-",
                        3,model.getNum() + 1,isMIn);
            }else if (model.getModel().topIsEmpty() && !model.getTopHaveGo()){
                stack.peek().setTop();
                return DFS(gridModels[model.getModel().getY() - 1][model.getModel().getX()],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",0)-",
                        2,model.getNum() + 1,isMIn);
            }else {
                stack.pop();
            }
        }else if (type == 7){
            if (model.getModel().leftIsEmpty() && !model.getLeftHaveGo()){
                stack.peek().setLeft();
                return DFS(gridModels[model.getModel().getY()][model.getModel().getX() - 1],
                    model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",3)-",
                    1,model.getNum() + 1,isMIn);
            } else if (model.getModel().topIsEmpty() && !model.getTopHaveGo()){
                stack.peek().setTop();
                return DFS(gridModels[model.getModel().getY() - 1][model.getModel().getX()],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",0)-",
                        2,model.getNum() + 1,isMIn);
            } else if (model.getModel().buttonIsEmpty() && !model.getButtonHaveGo()){
                stack.peek().setButton();
                return DFS(gridModels[model.getModel().getY() + 1][model.getModel().getX()],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",2)-",
                        0,model.getNum() + 1,isMIn);
            } else if (model.getModel().rightIsEmpty() && !model.getRightHaveGo()){
                stack.peek().setRight();
                return DFS(gridModels[model.getModel().getY()][model.getModel().getX() + 1],
                        model.getContent() + "(" + model.getModel().getX() + "," + model.getModel().getY() + ",1)-",
                        3,model.getNum() + 1,isMIn);
            }else {
                stack.pop();
            }
        }
        return msg;
    }
    private static int MIN = 99999999;
    /**
     * 深度遍历
     * */
    private static String DFS(GridModel model,String content,int inType,int num,boolean isMIn){
        maps.put(model,"11");
        String msg = null;
        if (isHavaGo(model.getX(),model.getY(),content))
            return content + "<fail>";
        if (num >= MIN && isMIn){
            return content + "<fail>";
        }
        if (ways[model.getY()][model.getX()] != 0 && ways[model.getY()][model.getX()] < num && isMIn)
            return content + "<fail>";
        if (ways[model.getY()][model.getX()] == 0 || ways[model.getY()][model.getX()] > num) ways[model.getY()][model.getX()] = num;
        if (model.getX() == endX && model.getY() == endY){
            System.out.printf("找到%7d条路径\n",deep++);
            MIN = MIN > num ? num : MIN;
            return content + "OK-";
        }
        if (model.isBranch() || (model.getY() == startX && model.getX() == startY && model.BranchNum() > 1)){
            StackModel stackModel = new StackModel(model,content,num);
            switch (inType){
                case 0:stackModel.setTop();break;
                case 1:stackModel.setRight();break;
                case 2:stackModel.setButton();break;
                case 3:stackModel.setLeft();break;
            }
           stack.push(stackModel);
            return content + "<stack>";
        }
        if (model.topIsEmpty() && inType != 0){
            msg = "(" + model.getX() + "," + model.getY() + ",0)-";
            return DFS(gridModels[model.getY() - 1][model.getX()],content + msg,2,num + 1,isMIn);
        }
        if (model.rightIsEmpty() && inType != 1){
            msg = "(" + model.getX() + "," + model.getY() + ",1)-";
            return DFS(gridModels[model.getY()][model.getX() + 1],content + msg,3,num + 1,isMIn);
        }
        if (model.buttonIsEmpty() && inType != 2){
            msg = "(" + model.getX() + "," + model.getY() + ",2)-";
            return DFS(gridModels[model.getY() + 1][model.getX()],content + msg,0,num + 1,isMIn);
        }
        if (model.leftIsEmpty() && inType != 3){
            msg = "(" + model.getX() + "," + model.getY() + ",3)-";
            return DFS(gridModels[model.getY()][model.getX() - 1],content + msg,1,num + 1,isMIn);
        }
        return content + "<fail>";
    }
    private static int getType(GridModel model){
        if ((endX - model.getY()) == 0){
            if (endY > model.getX()) return 6;
            else return 4;
        }else if (endY == model.getX()){
            if (endX > model.getY()) return 5;
            else return 7;
        }
        else if (endY > model.getX() && endX > model.getY())
            return 2;
        else if (endY < model.getX() && endX < model.getY())
            return 1;
        else if (endY < model.getX() && endX > model.getY())
            return 0;
        else
            return 3;
    }


    private static Queue<GridModel> queue = new ConcurrentLinkedQueue<>();
    private static Map<GridModel,Object> maps = new HashMap<>();
    /**
     * 广度遍历迷宫
     * */
    private static boolean BFS(){
        if (queue.isEmpty()) return false;
        GridModel model = queue.poll();
        maps.put(model,"11");
        if (model.getX() == endX && model.getY() == endY){
            MESSAGE = "11";
            return true;
        }
        if (model.topIsEmpty() && maps.get(gridModels[model.getY() - 1][model.getX()]) == null && !queue.contains(gridModels[model.getY() - 1][model.getX()])){
            queue.add(gridModels[model.getY() - 1][model.getX()]);
        }
        if (model.rightIsEmpty() && maps.get(gridModels[model.getY()][model.getX() + 1]) == null && !queue.contains(gridModels[model.getY()][model.getX() + 1])){
            queue.add(gridModels[model.getY()][model.getX() + 1]);
        }
        if (model.buttonIsEmpty() && maps.get(gridModels[model.getY() + 1][model.getX()]) == null && !queue.contains(gridModels[model.getY() + 1][model.getX()])){
            queue.add(gridModels[model.getY() + 1][model.getX()]);
        }
        if (model.leftIsEmpty() && maps.get(gridModels[model.getY()][model.getX() - 1]) == null && !queue.contains(gridModels[model.getY()][model.getX() - 1])){
            queue.add(gridModels[model.getY()][model.getX() - 1]);
        }
        return BFS();
    }


    /**
     * 本次深度遍历是否遍历了x,y点的格子
     * */
    private static boolean isHavaGo(int x,int y,String msg){
        try {
            if (msg.matches(".*\\(" + x + "," + y + ",\\d{1}\\).*"))
                return true;
        }catch (Exception e){}
        return false;
    }


    /**
     * 产生随机迷宫
     * */
    public static void createMaze(){
        for (int i = 0;i < N;i++){
            for (int j = 0;j < M;j++){
                gridModels[i][j].setX(j).setY(i);
                //随机给格子的上边为空
                if (i != 0){
                    if (Math.random()*100 < 60){
                        gridModels[i][j].setTopIsEmpty();
                        gridModels[i - 1][j].setButtonIsEmpty();
                    }else {
                        gridModels[i][j].setTop();
                        gridModels[i - 1][j].setButton();
                    }
                }
                //随机给格子的右边为空
                if (j != M - 1){
                    if (Math.random()*100 < 60){
                        gridModels[i][j].setRightIsEmpty();
                        gridModels[i][j + 1].setLeftIsEmpty();
                    }else {
                        gridModels[i][j].setRight();
                        gridModels[i][j + 1].setLeft();
                    }
                }
                //随机给格子的下边为空
                if (i != N - 1){
                    if (Math.random()*100 < 60){
                        gridModels[i][j].setButtonIsEmpty();
                        gridModels[i + 1][j].setTopIsEmpty();
                    }else {
                        gridModels[i][j].setButton();
                        gridModels[i + 1][j].setTop();
                    }
                }
                //随机给格子的左边为空
                if (j != 0){
                    if (Math.random()*100 < 60){
                        gridModels[i][j].setLeftIsEmpty();
                        gridModels[i][j - 1].setRightIsEmpty();
                    }else {
                        gridModels[i][j].setLeft();
                        gridModels[i][j - 1].setRight();
                    }
                }
            }
        }
    }

    public static GridModel[][] getGridModels() {
        return gridModels;
    }

    public static int[][] getWays() {
        return ways;
    }

    static {
        init();
    }
    public static void main(String[] args) {
        init();
//        gridModels[0][0].setX(0).setY(0).setRightIsEmpty().setButtonIsEmpty();
//        gridModels[0][1].setX(1).setY(0).setLeftIsEmpty().setRightIsEmpty();
//        gridModels[0][2].setX(2).setY(0).setLeftIsEmpty().setButtonIsEmpty();
//        gridModels[1][0].setX(0).setY(1).setRightIsEmpty().setButtonIsEmpty();
//        gridModels[1][1].setX(1).setY(1).setLeftIsEmpty();
//        gridModels[1][2].setX(2).setY(1).setTopIsEmpty().setButtonIsEmpty();
//        gridModels[2][0].setX(0).setY(2).setTopIsEmpty().setRightIsEmpty();
//        gridModels[2][1].setX(1).setY(2).setLeftIsEmpty().setRightIsEmpty();
//        gridModels[2][2].setX(2).setY(2).setLeftIsEmpty().setTopIsEmpty();
//        setSartXY(0,0);
//        setEndXY(1,1);
        createMaze();
        for (GridModel[] gridModels:gridModels){
            for (GridModel model:gridModels)
                System.out.print("0x" + Integer.toHexString(model.getMsg()) + " ");
            System.out.println();
        }
    }
}

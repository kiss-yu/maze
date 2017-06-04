package sample;

import java.io.Serializable;

/**
 * Created by 11723 on 2017/5/29.
 */
public class GridModel implements Serializable,Comparable<GridModel>{
    private int x;
    private int y;
    private char msg = 0xff;//迷宫格子4个方向挡板信息 11111111 上左下右 四位

    public GridModel(){}
    public GridModel(int x,int y,char msg){
        this.x = x;
        this.y = y;
        this.msg = msg;
    }
    public GridModel(int x,int y){
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }

    public GridModel setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public GridModel setY(int y) {
        this.y = y;
        return this;
    }

    public char getMsg() {
        return msg;
    }

    public void setMsg(char msg) {
        this.msg = msg;
    }
    /**
     * 设置哪个方向没有挡板
     * */
    public GridModel setTopIsEmpty(){
        this.msg &= 0x7f;
        return this;
    }
    public GridModel setRightIsEmpty(){
        this.msg &= 0xbf;
        return this;
    }
    public GridModel setButtonIsEmpty(){
        this.msg &= 0xdf;
        return this;
    }
    public GridModel setLeftIsEmpty(){
        this.msg &= 0xef;
        return this;
    }

    /**
     * 设置哪个方向有挡板
     * */
    public GridModel setTop(){
        this.msg |= 0x80;
        return this;
    }
    public GridModel setRight(){
        this.msg |= 0x40;
        return this;
    }
    public GridModel setButton(){
        this.msg |= 0x20;
        return this;
    }
    public GridModel setLeft(){
        this.msg |= 0x10;
        return this;
    }

    /**
     * 获取格子上方是否有挡板 有时返回false
     * */
    public boolean topIsEmpty(){
        return !((msg&0x80) == 0x80);
    }
    public boolean rightIsEmpty(){
        return !((msg&0x40) == 0x40);
    }
    public boolean buttonIsEmpty(){
        return !((msg&0x20) == 0x20);
    }
    public boolean leftIsEmpty(){
        return !((msg&0x10) == 0x10);
    }
    /**
     * 是否是一个岔路口
     * */
    public boolean isBranch(){
        int i = 0;
        if (topIsEmpty()) i++;
        if (leftIsEmpty()) i++;
        if (buttonIsEmpty()) i++;
        if (rightIsEmpty()) i++;
        return i > 2;
    }


    public int BranchNum(){
        int i = 0;
        if (topIsEmpty()) i++;
        if (leftIsEmpty()) i++;
        if (buttonIsEmpty()) i++;
        if (rightIsEmpty()) i++;
        return i;
    }
    @Override
    public int compareTo(GridModel o) {
        return 0;
    }
}

package sample;

/**
 * Created by 11723 on 2017/5/31.
 */
public class StackModel {
    private GridModel model;
    private String content;
    private char type;
    private int num;
    public StackModel(){}
    public StackModel(GridModel model,String content,int num){
        this.model = model;
        this.content = content;
        this.num = num;
    }
    public GridModel getModel() {
        return model;
    }

    public void setModel(GridModel model) {
        this.model = model;
    }

    public String getContent() {
        return content;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public char getType() {
        return type;
    }
    public boolean getTopHaveGo(){
        return (type&0x80) == 0x80;
    }
    public boolean getRightHaveGo(){
        return (type&0x40) == 0x40;
    }
    public boolean getButtonHaveGo(){
        return (type&0x20) == 0x20;
    }
    public boolean getLeftHaveGo(){
        return (type&0x10) == 0x10;
    }
    public void setType(char type) {
        this.type = type;
    }
    public void setTop(){
        type |= 0x80;
    }
    public void setRight(){
        type |= 0x40;
    }
    public void setButton(){
        type |= 0x20;
    }
    public void setLeft(){
        type |= 0x10;
    }
}

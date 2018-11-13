import java.util.ArrayList;
import java.util.List;

public class Snake {
    private List<SnakeSection> sections;
    private boolean isAlive;
    private SnakeDirection direction;

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    public List<SnakeSection> getSections() {
        return sections;
    }

    public Snake(int x, int y) {
        sections = new ArrayList<>();
        sections.add(new SnakeSection(x,y));
        isAlive = true;
    }
    public int getX(){
        return sections.get(0).getX();
    }
    public int getY(){
        return sections.get(0).getY();
    }

    public void move(){
        if(isAlive == false)return;
        if(direction==SnakeDirection.UP){
            move(0,-1);
        }
        if(direction==SnakeDirection.RIGHT){
            move(1,0);
        }
        if(direction==SnakeDirection.DOWN){
            move(0,1);
        }
        if(direction==SnakeDirection.LEFT){
            move(-1,0);
        }
    }

    public void checkBorders(SnakeSection head){
        if ((head.getX()>=Room.game.getWidth()) ||(head.getY()>=Room.game.getHeight()) ||(head.getX()<0) ||(head.getY()<0)){
            isAlive=false;
        }
    }

    public void checkBody(SnakeSection head){
        if(sections.contains(head)){
            isAlive=false;
        }
    }

    public void move (int x,int y){
        SnakeSection head = new SnakeSection(
                sections.get(0).getX()+x,
                sections.get(0).getY()+ y);
        checkBorders(head);
        checkBody(head);

        if(isAlive){
            sections.add(0,head);
            if(Room.game.getMouse().getX() == head.getX()&&
                    Room.game.getMouse().getY()==head.getY())
                Room.game.eatMouse();
            else sections.remove(sections.size()-1);}

    }

    public boolean isAlive() {
        return isAlive;
    }

    public SnakeDirection getDirection() {
        return direction;
    }
}

import java.awt.event.KeyEvent;

public class Room {
    private int width,height;
    private Snake snake;
    private Mouse mouse;

    public static Room game;

    public Room(int width, int height, Snake snake) {
        this.width = width;
        this.height = height;
        this.snake = snake;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void setMouse(Mouse mouse) {
        this.mouse = mouse;
    }

    public void createMouse(){
        int x = (int) (Math.random()*width);
        int y = (int) (Math.random()*height);
        mouse = new Mouse(x,y);
    }

    public void eatMouse(){
        createMouse();
    }


    public void run() throws InterruptedException {
        //Создаем объект "наблюдатель за клавиатурой" и стартуем его.
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        while (snake.isAlive()) {

            if (keyboardObserver.hasKeyEvents()) {
                KeyEvent event = keyboardObserver.getEventFromTop();

                if (event.getKeyChar() == 'q') return;


                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    snake.setDirection(SnakeDirection.LEFT);
                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    snake.setDirection(SnakeDirection.RIGHT);

                else if (event.getKeyCode() == KeyEvent.VK_UP)
                    snake.setDirection(SnakeDirection.UP);
                else if (event.getKeyCode() == KeyEvent.VK_DOWN)
                    snake.setDirection(SnakeDirection.DOWN);
            }

            snake.move();
            print();
            sleep();
        }

        System.out.println("Game Over!");
    }

    public void print() {
        int[][] arr = new int[width][height];

        for (int i = 0; i < snake.getSections().size(); i++) {
            if (i == 0) {
                arr[snake.getX()][snake.getY()] = 2;
            } else
                arr[snake.getSections().get(i).getX()][snake.getSections().get(i).getY()] = 1;
        }
        arr[mouse.getX()][mouse.getY()] = 3;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (arr[j][i] == 3) {
                    System.out.print('^');
                } else if (arr[j][i] == 2) {
                    System.out.print('X');
                } else if (arr[j][i] == 1){
                    System.out.print('x');
                } else {
                    System.out.print(".");
                }

            }
            System.out.println();
        }
        System.out.println();
        System.out.println();

    }

    public void sleep() throws InterruptedException {
        int slep = 520;
        if(snake.getSections().size()<=15)
            slep = slep-20*snake.getSections().size();
        else slep = 200;

        Thread.sleep(slep);
    }

    public static void main(String[] args) throws InterruptedException {
        Snake snake = new Snake(1,1);
        game = new Room(20,20,snake);
        game.getSnake().setDirection(SnakeDirection.DOWN);
        game.createMouse();
        game.run();
    }
}
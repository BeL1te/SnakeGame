package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private static final int GOAL = 28;
    private int score;

    private Snake snake;
    private Apple apple;

    private int turnDelay;
    private boolean isGameStopped;

    private void createGame() {
        score = 0;
        setScore(score);
        Snake snake1 = new com.javarush.games.snake.Snake(WIDTH / 2, HEIGHT / 2);
        snake = snake1;
        createNewApple();
        isGameStopped = false;
        drawScene();
        turnDelay = 300;
        setTurnTimer(turnDelay);
    }

    private void drawScene() {
        for (int i = 0; i < WIDTH; ++i) {
            for (int j = 0; j < HEIGHT; ++j) {
                setCellValueEx(i, j, Color.GREEN, "");
            }
        }
        apple.draw(this);
        snake.draw(this);
    }

    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        showGrid(false);
        createGame();
    }

    public void onTurn(int step) {
        if (!apple.isAlive) {
            createNewApple();
            score += 5;
            setScore(score);
            turnDelay -= 10;
            setTurnTimer(turnDelay);
        }
        snake.move(apple);
        if (!snake.isAlive) {
            gameOver();
        }
        if (snake.getLength() > GOAL) {
            win();
        }
        drawScene();
    }

    public void onKeyPress(Key key) {
        if (Key.LEFT == key) {
            snake.setDirection(Direction.LEFT);
        }
        if (Key.RIGHT == key) {
            snake.setDirection(Direction.RIGHT);
        }
        if (Key.DOWN == key) {
            snake.setDirection(Direction.DOWN);
        }
        if (Key.UP == key) {
            snake.setDirection(Direction.UP);
        }
        if (Key.SPACE == key && isGameStopped) {
            createGame();
        }
    }

    private void createNewApple() {
        while (true) {
            int x = getRandomNumber(WIDTH);
            int y = getRandomNumber(HEIGHT);
            Apple apple1 = new Apple(x, y);
            apple = apple1;
            if (!snake.checkCollision(apple)) {
                break;
            }
        }
    }

    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "GAME OVER", Color.BLACK, 75);
    }

    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.NONE, "YOU WIN", Color.BLUE, 75);
    }
}
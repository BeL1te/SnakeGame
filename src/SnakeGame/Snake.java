package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    int x, y;
    public boolean isAlive = true;
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    private List<GameObject> snakeParts = new ArrayList<>();


    private Direction direction = Direction.LEFT;

    public void setDirection(Direction newDirection) {
        if (((direction == Direction.RIGHT) || (direction == Direction.LEFT)) && (snakeParts.get(0).y == snakeParts.get(1).y) && ((newDirection == Direction.DOWN) || (newDirection == Direction.UP))) {
            this.direction = newDirection;
        }
        if (((direction == Direction.DOWN) || (direction == Direction.UP)) && (snakeParts.get(0).x == snakeParts.get(1).x) && ((newDirection == Direction.RIGHT) || (newDirection == Direction.LEFT))) {
            this.direction = newDirection;
        }
    }

    public Snake(int x, int y) {
        this.x = x;
        this.y = y;
        snakeParts.add(new GameObject(x, y));
        snakeParts.add(new GameObject(x + 1, y));
        snakeParts.add(new GameObject(x + 2, y));
    }

    public void draw(Game game) {
        if (isAlive) {
            game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, Color.ORANGE, 75);
            for (int i = 1; i < snakeParts.size(); i++) {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.ORANGE, 75);
            }

        } else {
            game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, Color.RED, 75);
            for (int i = 1; i < snakeParts.size(); i++) {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.RED, 75);
            }
        }
    }

    public void move(Apple apple) {
        GameObject newHead = createNewHead();
        if (checkCollision(newHead)) {
            isAlive = false;
        } else {
            if (newHead.x < 0 || newHead.x >= SnakeGame.WIDTH || newHead.y < 0 || newHead.y >= SnakeGame.HEIGHT) {
                isAlive = false;
            } else if (newHead.x == apple.x && newHead.y == apple.y) {
                apple.isAlive = false;
                snakeParts.add(0, newHead);
            } else {
                snakeParts.add(0, newHead);
                removeTail();
            }
        }
    }

    public GameObject createNewHead() {
        int headX = snakeParts.get(0).x;
        int headY = snakeParts.get(0).y;

        if (direction == Direction.LEFT) {
            return new GameObject(headX - 1, headY);
        } else if (direction == Direction.RIGHT) {
            return new GameObject(headX + 1, headY);
        } else if (direction == Direction.DOWN) {
            return new GameObject(headX, headY + 1);
        } else {
            return new GameObject(headX, headY - 1);
        }
    }

    public boolean checkCollision(GameObject gameObject) {
        for (GameObject obj : snakeParts) {
            if (gameObject.x == obj.x && gameObject.y == obj.y) {
                return true;
            }
        }
        return false;
    }

    public int getLength() {
        return snakeParts.size();
    }


    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }
}
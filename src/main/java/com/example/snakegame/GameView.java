package com.example.snakegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.RectF;
import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {
    private static final int GRID_SIZE = 20;
    private float cellSize;
    private ArrayList<Point> snake;
    private Point food;
    private Direction direction;
    private boolean isGameOver;
    private int score;
    private Paint snakePaint;
    private Paint foodPaint;
    private Paint borderPaint;
    private GameOverListener gameOverListener;
    private float touchStartX, touchStartY;

    public enum Direction {
        UP, RIGHT, DOWN, LEFT
    }

    private static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public interface GameOverListener {
        void onGameOver(int score);
    }

    public GameView(Context context) {
        super(context);
        init();
    }

    private void init() {
        snake = new ArrayList<>();
        snake.add(new Point(GRID_SIZE/2, GRID_SIZE/2));
        direction = Direction.RIGHT;
        isGameOver = false;
        score = 0;

        snakePaint = new Paint();
        snakePaint.setColor(0xFFFF69B4);
        foodPaint = new Paint();
        foodPaint.setColor(0xFFFF69B4);
        borderPaint = new Paint();
        borderPaint.setColor(0xFFFF69B4);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(4);

        spawnFood();
        startGameLoop();
    }

    public void setGameOverListener(GameOverListener listener) {
        this.gameOverListener = listener;
    }

    private void startGameLoop() {
        post(new Runnable() {
            @Override
            public void run() {
                if (!isGameOver) {
                    updateGame();
                    invalidate();
                    postDelayed(this, 200); // Update every 200ms
                }
            }
        });
    }

    private void spawnFood() {
        Random random = new Random();
        do {
            food = new Point(random.nextInt(GRID_SIZE), random.nextInt(GRID_SIZE));
        } while (isOnSnake(food));
    }

    private boolean isOnSnake(Point point) {
        for (Point p : snake) {
            if (p.x == point.x && p.y == point.y) return true;
        }
        return false;
    }

    private void updateGame() {
        Point head = snake.get(0);
        Point newHead = new Point(head.x, head.y);

        switch (direction) {
            case UP: newHead.y--; break;
            case RIGHT: newHead.x++; break;
            case DOWN: newHead.y++; break;
            case LEFT: newHead.x--; break;
        }

        // Check for collisions
        if (newHead.x < 0 || newHead.x >= GRID_SIZE || 
            newHead.y < 0 || newHead.y >= GRID_SIZE || 
            isOnSnake(newHead)) {
            gameOver();
            return;
        }

        snake.add(0, newHead);

        // Check if food is eaten
        if (newHead.x == food.x && newHead.y == food.y) {
            score++;
            spawnFood();
        } else {
            snake.remove(snake.size() - 1);
        }
    }

    private void gameOver() {
        isGameOver = true;
        if (gameOverListener != null) {
            gameOverListener.onGameOver(score);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cellSize = Math.min(w, h) / (float) GRID_SIZE;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float boardSize = cellSize * GRID_SIZE;
        
        // Draw border
        canvas.drawRect(0, 0, boardSize, boardSize, borderPaint);

        // Draw snake
        for (Point p : snake) {
            float left = p.x * cellSize;
            float top = p.y * cellSize;
            canvas.drawRect(left, top, left + cellSize, top + cellSize, snakePaint);
        }

        // Draw food
        float left = food.x * cellSize;
        float top = food.y * cellSize;
        canvas.drawCircle(left + cellSize/2, top + cellSize/2, cellSize/2, foodPaint);
    }

    public void setDirection(Direction newDirection) {
        // Prevent 180-degree turns
        switch (newDirection) {
            case UP:
                if (direction != Direction.DOWN) direction = newDirection;
                break;
            case DOWN:
                if (direction != Direction.UP) direction = newDirection;
                break;
            case LEFT:
                if (direction != Direction.RIGHT) direction = newDirection;
                break;
            case RIGHT:
                if (direction != Direction.LEFT) direction = newDirection;
                break;
        }
    }
} 
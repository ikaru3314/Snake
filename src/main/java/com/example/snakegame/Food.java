package com.example.snakegame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Food {

    private final Rectangle food;

    private final Random random =
            new Random();

    private static final int TAMANHO = 600;

    public Food() {

        food = new Rectangle(
                20,
                20
        );

        food.setFill(
                Color.RED
        );

        spawn();
    }

    public Rectangle getFood() {

        return food;
    }

    public void spawn() {

        int x =
                random.nextInt(
                        TAMANHO / 20
                ) * 20;

        int y =
                random.nextInt(
                        TAMANHO / 20
                ) * 20;

        food.setX(x);

        food.setY(y);
    }
}
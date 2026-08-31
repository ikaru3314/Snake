package com.example.snakegame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Snake {

    private final List<Rectangle> body =
            new ArrayList<>();

    private Direction direction =
            Direction.RIGHT;

    public Snake() {

        reset();
    }

    public void reset() {

        body.clear();

        direction = Direction.RIGHT;

        // Cabeça
        Rectangle head =
                new Rectangle(20, 20);

        head.setFill(
                Color.LIMEGREEN
        );

        head.setX(300);
        head.setY(300);

        body.add(head);


        // Corpo
        for (int i = 1; i < 3; i++) {

            Rectangle part =
                    new Rectangle(20, 20);

            part.setFill(
                    Color.GREEN
            );

            part.setX(
                    300 - (i * 20)
            );

            part.setY(300);

            body.add(part);
        }
    }

    public List<Rectangle> getBody() {

        return body;
    }

    public Direction getDirection() {

        return direction;
    }

    public void setDirection(
            Direction direction
    ) {

        this.direction = direction;
    }

    public void move() {

        Rectangle head =
                body.get(0);

        double newX =
                head.getX();

        double newY =
                head.getY();


        switch (direction) {

            case UP:

                newY -= 20;

                break;

            case DOWN:

                newY += 20;

                break;

            case LEFT:

                newX -= 20;

                break;

            case RIGHT:

                newX += 20;

                break;
        }


        // Corpo acompanha
        for (int i = body.size() - 1;
             i > 0;
             i--) {

            body.get(i).setX(
                    body.get(i - 1).getX()
            );

            body.get(i).setY(
                    body.get(i - 1).getY()
            );
        }


        // Move cabeça
        head.setX(newX);
        head.setY(newY);
    }

    public void grow() {

        Rectangle last =
                body.get(
                        body.size() - 1
                );

        Rectangle newPart =
                new Rectangle(20, 20);

        newPart.setFill(
                Color.GREEN
        );

        newPart.setX(
                last.getX()
        );

        newPart.setY(
                last.getY()
        );

        body.add(newPart);
    }
}
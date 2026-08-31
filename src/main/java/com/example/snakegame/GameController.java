package com.example.snakegame;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameController {

    private static final int TAMANHO = 600;
    private static final long VELOCIDADE = 150_000_000L;

    private final Pane root;
    private final Scene scene;
    private final Runnable voltarMenu;

    private final Snake snake;
    private final Food food;

    private final Text scoreText;
    private final Text gameOverText;

    private final Button novoJogoButton;
    private final Button menuButton;

    private AnimationTimer timer;

    private boolean jogando = false;

    private int score = 0;

    private long ultimoMovimento = 0;

    public GameController(
            Pane root,
            Scene scene,
            Runnable voltarMenu
    ) {

        this.root = root;
        this.scene = scene;
        this.voltarMenu = voltarMenu;

        root.setPrefSize(TAMANHO, TAMANHO);

        root.setFocusTraversable(true);

        root.setStyle(
                "-fx-background-color: #111111;"
        );

        snake = new Snake();

        food = new Food();

        // PONTUAÇÃO
        scoreText = new Text(
                "Pontuação: 0"
        );

        scoreText.setX(15);
        scoreText.setY(30);

        scoreText.setFill(Color.WHITE);

        scoreText.setFont(
                Font.font(20)
        );

        // GAME OVER
        gameOverText = new Text();

        gameOverText.setX(180);
        gameOverText.setY(280);

        gameOverText.setFill(Color.WHITE);

        gameOverText.setFont(
                Font.font(40)
        );

        gameOverText.setVisible(false);

        // BOTÃO NOVO JOGO
        novoJogoButton = new Button(
                "NOVO JOGO"
        );

        novoJogoButton.setPrefWidth(160);
        novoJogoButton.setPrefHeight(45);

        novoJogoButton.setLayoutX(220);
        novoJogoButton.setLayoutY(450);

        estilizarBotao(novoJogoButton);

        novoJogoButton.setOnAction(
                event -> iniciarNovoJogo()
        );

        // BOTÃO MENU
        menuButton = new Button(
                "MENU"
        );

        menuButton.setPrefWidth(160);
        menuButton.setPrefHeight(45);

        menuButton.setLayoutX(220);
        menuButton.setLayoutY(505);

        menuButton.setStyle(
                "-fx-background-color: #333333;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8px;"
        );

        menuButton.setOnAction(event -> {

            if (timer != null) {
                timer.stop();
            }

            voltarMenu.run();
        });

        // ADICIONA ELEMENTOS
        root.getChildren().add(scoreText);

        root.getChildren().add(
                food.getFood()
        );

        root.getChildren().add(
                gameOverText
        );

        root.getChildren().add(
                novoJogoButton
        );

        root.getChildren().add(
                menuButton
        );

        root.getChildren().addAll(
                snake.getBody()
        );

        // ESCONDE BOTÕES
        novoJogoButton.setVisible(false);

        menuButton.setVisible(false);

        // TECLADO
        scene.setOnKeyPressed(event ->
                controlarDirecao(event.getCode())
        );

        // TIMER
        criarTimer();

        // COMEÇA O JOGO
        iniciarNovoJogo();
    }

    // ==========================================
    // CONTROLES
    // ==========================================

    private void controlarDirecao(KeyCode tecla) {

        switch (tecla) {

            case UP:
            case W:

                if (snake.getDirection()
                        != Direction.DOWN) {

                    snake.setDirection(
                            Direction.UP
                    );
                }

                break;

            case DOWN:
            case S:

                if (snake.getDirection()
                        != Direction.UP) {

                    snake.setDirection(
                            Direction.DOWN
                    );
                }

                break;

            case LEFT:
            case A:

                if (snake.getDirection()
                        != Direction.RIGHT) {

                    snake.setDirection(
                            Direction.LEFT
                    );
                }

                break;

            case RIGHT:
            case D:

                if (snake.getDirection()
                        != Direction.LEFT) {

                    snake.setDirection(
                            Direction.RIGHT
                    );
                }

                break;

            default:
                break;
        }
    }

    // ==========================================
    // TIMER
    // ==========================================

    private void criarTimer() {

        timer = new AnimationTimer() {

            @Override
            public void handle(long agora) {

                if (!jogando) {
                    return;
                }

                if (agora - ultimoMovimento
                        >= VELOCIDADE) {

                    snake.move();

                    verificarComida();

                    verificarColisao();

                    ultimoMovimento = agora;
                }
            }
        };
    }

    // ==========================================
    // NOVO JOGO
    // ==========================================

    private void iniciarNovoJogo() {

        if (timer != null) {
            timer.stop();
        }

        // Remove cobra antiga
        root.getChildren().removeAll(
                snake.getBody()
        );

        // Reinicia cobra
        snake.reset();

        // Direção inicial
        snake.setDirection(
                Direction.RIGHT
        );

        // Adiciona cobra novamente
        root.getChildren().addAll(
                snake.getBody()
        );

        // Nova comida
        food.spawn();

        // Zera pontuação
        score = 0;

        scoreText.setText(
                "Pontuação: 0"
        );

        // Esconde Game Over
        gameOverText.setVisible(false);

        novoJogoButton.setVisible(false);

        menuButton.setVisible(false);

        // Jogo ativo
        jogando = true;

        ultimoMovimento =
                System.nanoTime();

        timer.start();

        root.requestFocus();
    }

    // ==========================================
    // VERIFICAR COMIDA
    // ==========================================

    private void verificarComida() {

        Rectangle cabeca =
                snake.getBody().get(0);

        Rectangle comida =
                food.getFood();

        if (cabeca.getX() == comida.getX()
                &&
                cabeca.getY() == comida.getY()) {

            snake.grow();

            score++;

            scoreText.setText(
                    "Pontuação: " + score
            );

            food.spawn();

            Rectangle novaParte =
                    snake.getBody().get(
                            snake.getBody().size() - 1
                    );

            if (!root.getChildren()
                    .contains(novaParte)) {

                root.getChildren()
                        .add(novaParte);
            }
        }
    }

    // ==========================================
    // COLISÃO
    // ==========================================

    private void verificarColisao() {

        Rectangle cabeca =
                snake.getBody().get(0);

        // Colisão com parede
        if (cabeca.getX() < 0
                ||
                cabeca.getX() >= TAMANHO
                ||
                cabeca.getY() < 0
                ||
                cabeca.getY() >= TAMANHO) {

            gameOver();

            return;
        }

        // Colisão com o próprio corpo
        for (int i = 1;
             i < snake.getBody().size();
             i++) {

            Rectangle parte =
                    snake.getBody().get(i);

            if (cabeca.getX()
                    == parte.getX()
                    &&
                    cabeca.getY()
                            == parte.getY()) {

                gameOver();

                return;
            }
        }
    }

    // ==========================================
    // GAME OVER
    // ==========================================

    private void gameOver() {

        jogando = false;

        if (timer != null) {
            timer.stop();
        }

        // Salva recorde
        ScoreManager.salvarRecorde(
                score
        );

        gameOverText.setText(
                "GAME OVER\n\nPontuação: "
                        + score
        );

        gameOverText.setVisible(true);

        novoJogoButton.setVisible(true);

        menuButton.setVisible(true);
    }

    // ==========================================
    // ESTILO DO BOTÃO
    // ==========================================

    private void estilizarBotao(Button botao) {

        botao.setStyle(
                "-fx-background-color: #32CD32;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8px;"
        );
    }
}
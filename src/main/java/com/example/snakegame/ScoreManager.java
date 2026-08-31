package com.example.snakegame;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScoreManager {

    private static final Path ARQUIVO =
            Paths.get("pontuacao.txt");

    public static int carregarRecorde() {

        try {

            if (Files.exists(ARQUIVO)) {

                String texto = new String(
                        Files.readAllBytes(ARQUIVO),
                        StandardCharsets.UTF_8
                ).trim();

                if (!texto.isEmpty()) {

                    return Integer.parseInt(texto);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0;
    }

    public static void salvarRecorde(int pontuacao) {

        try {

            int recordeAtual =
                    carregarRecorde();

            if (pontuacao > recordeAtual) {

                Files.write(
                        ARQUIVO,
                        String.valueOf(pontuacao)
                                .getBytes(StandardCharsets.UTF_8)
                );
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
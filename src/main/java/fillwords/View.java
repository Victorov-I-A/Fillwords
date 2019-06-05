package fillwords;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class View extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Controller controller = new Controller();

        controller.borderPane.setMinWidth(1000);
        controller.borderPane.setMinHeight(700);

        controller.enterWord.setText("ВВЕСТИ СЛОВО");
        controller.enterWord.setPrefSize(200, 50);
        controller.enterWord.setDisable(true);

        controller.reset.setText("СБРОС");
        controller.reset.setPrefSize(200, 50);
        controller.reset.setDisable(true);

        controller.endGame.setText("КОНЕЦ ИГРЫ");
        controller.endGame.setPrefSize(200, 50);
        controller.endGame.setDisable(true);

        controller.firstPlayerArea.setText("Очки первого игрока:\n0");
        controller.firstPlayerArea.setPrefSize(200, 50);

        controller.secondPlayerArea.setText("Очки второго игрока:\n0");
        controller.secondPlayerArea.setPrefSize(200, 50);

        controller.enterField.setPromptText("ВВЕДИТЕ КОЛИЧЕСТВО СЛОВ");
        controller.enterField.setPrefSize(200, 50);

        controller.startButton.setText("НАЧАТЬ ИГРУ");
        controller.startButton.setPrefSize(200, 50);
        controller.startButton.setDisable(true);

        controller.console.setPrefSize(200, 50);
        controller.console.setDisable(true);

        Group root = new Group();
        root.getChildren().add(controller.borderPane);
        primaryStage.setTitle("Fillwords!");
        primaryStage.setScene(new Scene(root, 1000, 700, Color.GREY));
        primaryStage.show();

        controller.enterField.textProperty().addListener( (o,old,newV) -> {
            if (!newV.isEmpty()) {
                try {
                    controller.setNumberOfWords(Integer.parseInt(newV));
                } catch (NumberFormatException e) {
                    controller.console.setText("Ошибка ввода");
                }
                controller.startButton.setDisable(false);
            }
        });

        controller.startButton.setOnMouseClicked(e -> {
            try {
                controller.start();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            controller.enterWord.setDisable(false);
            controller.reset.setDisable(false);
            controller.endGame.setDisable(false);
            controller.console.setDisable(false);
            controller.startButton.setText("ПЕРЕЗАПУСК");
            controller.enterField.setText("");
            controller.console.setText("Ход первого игрока");

            for (int y = 0; y < controller.sqrt; y++) {
                for (int x = 0; x < controller.sqrt; x++) {
                    int finalX = x;
                    int finalY = y;
                    controller.matrix[x][y].setOnMouseClicked( t -> {
                        controller.matrixAction(finalX, finalY);
                    });
                }
            }
        });

        controller.enterWord.setOnMouseClicked( e -> {
            controller.enterWordAction();

            if (controller.listOfWords.isEmpty()) {
                controller.enterWord.setDisable(true);
                controller.reset.setDisable(true);
                controller.endGame.setDisable(true);
                controller.firstPlayerArea.setDisable(true);
                controller.secondPlayerArea.setDisable(true);
                for (int y = 0; y < controller.sqrt; y++) {
                    for (int x = 0; x < controller.sqrt; x++) {
                        controller.matrix[x][y].setStyle("-fx-background-color: grey");
                        controller.matrix[x][y].setDisable(false);
                    }
                }

                controller.endGameAction();
            }
        });

        controller.reset.setOnMouseClicked( e -> controller.resetAction());

        controller.endGame.setOnMouseClicked( e -> {
            controller.enterWord.setDisable(true);
            controller.reset.setDisable(true);
            controller.endGame.setDisable(true);
            controller.firstPlayerArea.setDisable(true);
            controller.secondPlayerArea.setDisable(true);
            for (int y = 0; y < controller.sqrt; y++) {
                for (int x = 0; x < controller.sqrt; x++) {
                    controller.matrix[x][y].setStyle("-fx-background-color: grey");
                    controller.matrix[x][y].setDisable(false);
                }
            }

            controller.endGameAction();
        });
    }
}

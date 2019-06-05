package fillwords;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Controller {
    Model model = new Model();
    private int numberOfWords;
    int sqrt;
    //матрица
    Button[][] matrix;
    //лист множеств букв всех возможных слов
    ArrayList<HashSet<Button>> listOfWords = new ArrayList<HashSet<Button>>();
    //множество букв, которых выбрал текущий игрок
    HashSet<Button> realTimeWord = new HashSet<>();
    //текущий игрок
    boolean realTimePlayer = false;
    //рейтинг первого игрока
    int firstPlayerRating = 0;
    //рейтинг второго игрока
    int secondPlayerRating = 0;

    final BorderPane borderPane = new BorderPane();
    //поле первого игрока
    final TextArea firstPlayerArea = new TextArea();
    //поле второго игрока
    final TextArea secondPlayerArea= new TextArea();
    //ввод слова
    final Button enterWord = new Button();
    //сброс выделенных полей
    final Button reset = new Button();
    //конец игры
    final Button endGame = new Button();
    //вводное поле
    final TextArea enterField = new TextArea();
    //начало\рестарт игры
    final Button startButton = new Button();
    //вывод сообщений
    final TextArea console = new TextArea();

    Controller() {
        borderPane.setLeft(new VBox(firstPlayerArea,
                secondPlayerArea,
                enterWord,
                reset,
                endGame,
                enterField,
                startButton,
                console
        ));
    }

    public void start() throws IOException {
        model.createMatrix(numberOfWords);
        sqrt = model.getMatrix().sqrt;
        matrix = new Button[sqrt][sqrt];
        for (int y = 0; y < sqrt; y++)
            for (int x = 0; x < sqrt; x++)
                matrix[x][y] = new Button();

        model.getSets().forEach(set -> {
            HashSet<Button> setOfButtons = new HashSet<>();
            set.forEach(pair -> setOfButtons.add(matrix[pair.getKey()][pair.getValue()]));
            listOfWords.add(setOfButtons);
        });
        VBox vBoxOfMatrix = new VBox();
        for (int y = 0; y < sqrt; y++) {
            HBox hBox = new HBox();
            for (int x = 0; x < sqrt; x++) {
                matrix[x][y].setText(model.getMatrix().matrix[x][y]);
                matrix[x][y].setPrefSize(50, 50);
                hBox.getChildren().add(matrix[x][y]);
            }
            vBoxOfMatrix.getChildren().add(hBox);
        }
        borderPane.setCenter(vBoxOfMatrix);
    }

    public void matrixAction(int x, int y) {
        realTimeWord.add(matrix[x][y]);
    }

    public void enterWordAction() {
        if (isRealTimePlayerInList()) {
            if (!realTimePlayer) {
                firstPlayerRating = firstPlayerRating + realTimeWord.size();
                firstPlayerArea.setText("Очки первого игрока:\n" + firstPlayerRating);
                realTimePlayer = true;
                console.setText("Ход второго игрока");
            } else {
                secondPlayerRating = secondPlayerRating + realTimeWord.size();
                secondPlayerArea.setText("Очки первого игрока:\n" + secondPlayerRating);
                realTimePlayer = false;
                console.setText("Ход первого игрока");
            }
            realTimeWord.forEach(button -> {
                button.setStyle("-fx-background-color: grey");
                button.setDisable(false);
            });
        } else
            console.setText("Такого слова нет");
        realTimeWord.clear();
    }

    public void resetAction() {
        realTimeWord.clear();
        console.setText("Сброс успешно выполнен");
    }

    public void endGameAction() {
        if (firstPlayerRating > secondPlayerRating)
            console.setText("Победитель - первый игрок,\nнабравший " + firstPlayerRating + " очков!");
        if (firstPlayerRating < secondPlayerRating)
            console.setText("Победитель - второй игрок,\n набравший " + secondPlayerRating + " очков!");
        if (firstPlayerRating == secondPlayerRating)
            console.setText("Победитель не выявлен,\n так как оба игрока набрали по " + firstPlayerRating + " очков!");
        realTimeWord.clear();
    }

    private boolean isRealTimePlayerInList() {
        for (HashSet<Button> set: listOfWords) {
            if (set.equals(realTimeWord)) {
                listOfWords.remove(set);
                return true;
            }
        }
        return false;
    }

    public void setNumberOfWords(int numberOfWords) {
        this.numberOfWords = numberOfWords;
    }
}
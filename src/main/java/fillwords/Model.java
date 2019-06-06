package fillwords;

import javafx.util.Pair;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Model {
    private ArrayList<HashSet<Pair<Integer, Integer>>> listOfSets;
    private SqrMatrix matrix;

    //создание матрицы
    public void createMatrix(int numberOfWord) throws IOException {
        Random random = new Random();
        ArrayList<String> words = randomWords(numberOfWord);
        matrix = new SqrMatrix((int) Math.pow(numberOfWord + Math.sqrt(numberOfWord), 2));
        ArrayList<Pair> listOfEmptyBox = matrix.listOfBoxes();
        ArrayList<HashSet<Pair<Integer, Integer>>> listOfSets = new ArrayList<>();

        for (String word: words) {
            listOfSets.add(new HashSet<>());
            int indexOfFirstBox = random.nextInt(listOfEmptyBox.size());
            int x = (int) listOfEmptyBox.get(indexOfFirstBox).getKey();
            int y = (int) listOfEmptyBox.get(indexOfFirstBox).getValue();

            matrix.matrix[x][y] = String.valueOf(word.charAt(0));
            listOfSets.get(listOfSets.size() - 1).add(new Pair<>(x, y));
            listOfEmptyBox.remove(indexOfFirstBox);

            for (int i = 1; i < word.length(); i++) {
                ArrayList<Pair> emptyAround = new ArrayList<>();

                if (listOfEmptyBox.contains(new Pair<>(x + 1, y)))
                    emptyAround.add(new Pair<>(x + 1, y));

                if (listOfEmptyBox.contains(new Pair<>(x, y + 1)))
                    emptyAround.add(new Pair<>(x, y + 1));

                if (listOfEmptyBox.contains(new Pair<>(x - 1, y)))
                    emptyAround.add(new Pair<>(x - 1, y));

                if (listOfEmptyBox.contains(new Pair<>(x, y - 1)))
                    emptyAround.add(new Pair<>(x, y - 1));

                if (emptyAround.size() == 0) {
                    listOfSets.remove(listOfSets.size() - 1);
                    break;
                }

                int indexOfBox = random.nextInt(emptyAround.size());
                x = (int) emptyAround.get(indexOfBox).getKey();
                y = (int) emptyAround.get(indexOfBox).getValue();

                matrix.matrix[x][y] = String.valueOf(word.charAt(i));
                listOfSets.get(listOfSets.size() - 1).add(new Pair<>(x, y));
                listOfEmptyBox.remove(new Pair<>(x, y));
            }
        }

        char[] rusAlphabet = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя".toCharArray();
        for (Pair coord: listOfEmptyBox) {
            matrix.matrix[(int) coord.getKey()][(int) coord.getValue()]
                     = String.valueOf(rusAlphabet[random.nextInt(rusAlphabet.length)]);
        }
        this.listOfSets = listOfSets;
    }

    private ArrayList<String> randomWords(int numberOfWord) throws IOException {
        ArrayList<String> array = new ArrayList<>();
        Random random = new Random();

        for (int i = 1; i <= numberOfWord; i++) {
            String word = Files.readAllLines(Paths.get("resourse/words.txt"))
                    .get(random.nextInt(
                            (int) Files.lines(Paths.get("resourse/words.txt")).count()
                            )
                    );
            if (!array.contains(word))
                array.add(word);
            else
                i--;
        }
        System.out.println(array);
        return array;
    }

    public SqrMatrix getMatrix() {
        return this.matrix;
    }

    public ArrayList<HashSet<Pair<Integer, Integer>>> getSets() {
        return listOfSets;
    }
}
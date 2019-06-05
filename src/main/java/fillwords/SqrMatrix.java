package fillwords;

import javafx.util.Pair;
import java.util.ArrayList;

import static java.lang.Math.*;

public class SqrMatrix {
    public final int sqrt;
    public String[][] matrix;
    //sqrt(boxNumber) must be without balance
    public SqrMatrix(int boxNumber) {
        sqrt = (int) sqrt(boxNumber);
        matrix = new String[sqrt][sqrt];

        for (int y = 0; y < sqrt; y++)
            for (int x = 0; x < sqrt; x++)
                matrix[x][y] = "";
    }

    public ArrayList<Pair> listOfBoxes() {
        ArrayList<Pair> list = new ArrayList<>();

        for (int i = 0; i < sqrt; i++)
            for (int j = 0; j < sqrt; j++) {
                list.add(new Pair<>(j, i));
            }

        return list;
    }
}

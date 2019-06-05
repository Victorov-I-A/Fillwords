package fillwords;

import javafx.scene.control.Button;

import javafx.util.Pair;
import java.util.ArrayList;

import static java.lang.Math.*;

public class SqrMatrix {
    final int sqrt;
    Button[][] matrix;
    //sqrt(boxNumber) must be without balance
    SqrMatrix(int boxNumber) {
        sqrt = (int) sqrt(boxNumber);
        matrix = new Button[sqrt][sqrt];

        for (int y = 0; y < sqrt; y++)
            for (int x = 0; x < sqrt; x++)
                matrix[x][y] = new Button();
    }

    public ArrayList<Pair> listOfBoxes() {
        ArrayList<Pair> list = new ArrayList<>();

        for (int i = 0; i < sqrt; i++)
            for (int j = 0; j < sqrt; j++) {
                list.add(new Pair(j, i));
            }

        return list;
    }
}

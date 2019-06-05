import fillwords.Model;
import fillwords.SqrMatrix;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;

public class FillwordsTests {

    @Test
    public void sqrMatrixTest() throws IOException {

        SqrMatrix matrix = new SqrMatrix(100);

        //проверка, правильно ли рассчитывается sqrt
        assertEquals(matrix.sqrt, 10);

        //проверка, верное ли количество пар координат в списке
        assertEquals(matrix.listOfBoxes().size(), 100);
    }

    @Test
    public void modelTest() throws IOException {

        Model model = new Model();
        model.createMatrix(10);
        SqrMatrix matrix = model.getMatrix();

        //проверка на то, заполненна ли каждая ячейка матрицы
        for (int y = 0; y < matrix.sqrt; y++) {
            for (int x = 0; x < matrix.sqrt; x++) {
                assertNotEquals(matrix.matrix[x][y], "");
            }
        }

        //проверка, есть ли хоть одно слово в списке
        assertNotEquals(model.getSets().size(), 0);
    }
}

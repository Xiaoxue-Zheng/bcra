package uk.ac.herc.bcra.algorithm.tyrercuzick.version8;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import uk.ac.herc.bcra.algorithm.AlgorithmException;

public class OutputStringTest {

    private final String TAB = "\t";

    @Test
    public void testStringGeneration() throws Exception {

        List<Object> array = new ArrayList<Object>();
        StringBuilder builder = new StringBuilder("");

        array.add("abc");
        builder.append("abc").append(TAB);

        array.add(123);
        builder.append("123").append(TAB);

        array.add(5.4321f);
        builder.append("5.43").append(TAB);

        array.add(true);
        builder.append("1").append(TAB);

        array.add(false);
        builder.append("0")/* no TAB after last item */;

        String output = OutputString.getOutputString(array);
        String string = builder.toString();

        assertThat(output).isEqualTo(string);
    }

    @Test
    public void testNullException() throws Exception {

        List<Object> array = new ArrayList<Object>();
        array.add(null);

        assertThrows(AlgorithmException.class, () -> {
            OutputString.getOutputString(array);
        });
    }
}

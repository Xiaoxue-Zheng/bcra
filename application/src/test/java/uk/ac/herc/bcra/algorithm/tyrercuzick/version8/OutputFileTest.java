package uk.ac.herc.bcra.algorithm.tyrercuzick.version8;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OutputFileTest {

    @Test
    public void testOutputFile() throws Exception {

        assertThat(
            OutputFile.getOutputFile("testline")
        )
        .isEqualTo(
            "v8\n1\ntestline\n"
        );
    }
}

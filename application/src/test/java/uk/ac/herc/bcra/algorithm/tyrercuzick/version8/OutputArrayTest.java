package uk.ac.herc.bcra.algorithm.tyrercuzick.version8;

import org.junit.jupiter.api.Test;

import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.Parent;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel.Relative;

import static org.assertj.core.api.Assertions.assertThat;

public class OutputArrayTest {

    /* This value was obtained from the IBIS Risk Evaluator v8.0b */
    private final Integer EMPTY_ALGORITHM_OUTPUT_ITEM_COUNT = 60;

    /* These values were obtained from the IBIS Risk Evaluator v8.0b and modified to match the specification */
    private final String EMPTY_ALGORITHM_OUTPUT_STRING = 
    /*       V Index 2 changed from <age> to -99 to match specification */
    /*              V Index 4 changed from 1 to 2 to match specification */
        "*	-99	-99	2	0	3	-99	-99	-99	0	0	0	0	0	-99	" +
    /*                  V Index 20 changed from 2 to 0 to match specification */
        "0	0	1	0	0	0	0	0	0	0	0	-99	-99	-99	0	" +
        "0	0	0	0	-99	-99	-99	0	0	0	0	-99	-99	-99	0	" +
        "0	0	0	0	0	0	0	0	0	0	0	0	0	-99	0";

    /* This value was obtained from the IBIS Risk Evaluator v8.0b */
    private final Integer RELATIVE_ALGORITHM_OUTPUT_ITEM_COUNT = 150;

    @Test
    public void testOutputSizeNoRelatives() throws Exception {

        AlgorithmModel emptyAlgorithm = new AlgorithmModel("participantidentifier");
        int size = OutputArray.getOutputArray(emptyAlgorithm).size();

        assertThat(size).isEqualTo(EMPTY_ALGORITHM_OUTPUT_ITEM_COUNT);
    }

    @Test
    public void testEmptyAlgorithmOutput() throws Exception {

        AlgorithmModel emptyAlgorithm = new AlgorithmModel("*");

        String output =
            OutputString.getOutputString(
                OutputArray.getOutputArray(emptyAlgorithm)
            );

        assertThat(output).isEqualTo(EMPTY_ALGORITHM_OUTPUT_STRING);
    }

    @Test
    public void testOutputSizeWithOneOfEachRelative() throws Exception {

        AlgorithmModel algorithm = new AlgorithmModel("participantidentifier");

        algorithm.daughters.add(new Relative());                    // 1 daughter

        Parent sister = new Parent();                               // 1 sister
        sister.daughters.add(new Relative());                       // 1 sister niece
        algorithm.sisters.add(sister);
        algorithm.unaffectedBrother.daughters.add(new Relative());  // 1 brother niece

        Parent maternalAunt = new Parent();                         // 1 maternal aunt
        maternalAunt.daughters.add(new Relative());                 // 1 m-a cousin
        algorithm.maternalAunts.add(maternalAunt);

        Parent paternalAunt = new Parent();                         // 1 paternal aunt
        paternalAunt.daughters.add(new Relative());                 // 1 p-a cousin
        algorithm.paternalAunts.add(paternalAunt);

        algorithm.unaffectedMaternalUncle.daughters.add(new Relative()); // 1 m-u cousin
        algorithm.unaffectedPaternalUncle.daughters.add(new Relative()); // 1 p-u cousin

        algorithm.paternalHalfSisters.add(new Relative());          // 1 paternal half sister
        algorithm.maternalHalfSisters.add(new Relative());          // 1 maternal half sister

        int size = OutputArray.getOutputArray(algorithm).size();

        assertThat(size).isEqualTo(RELATIVE_ALGORITHM_OUTPUT_ITEM_COUNT);
    }
}

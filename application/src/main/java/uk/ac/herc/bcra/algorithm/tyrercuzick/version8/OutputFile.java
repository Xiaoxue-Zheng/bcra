package uk.ac.herc.bcra.algorithm.tyrercuzick.version8;

import java.util.List;

public class OutputFile {

    private static final String NEWLINE = "\n";

    public static String mapAlgorithmToFile(AlgorithmModel algorithm) {

        List<Object> outputArray = OutputArray.getOutputArray(algorithm);

        String participantLine = OutputString.getOutputString(outputArray);

        return getOutputFile(participantLine);  
    }

    public static String getOutputFile(String singleParticipantLine) {

        String file = "";

        file += "v8" + NEWLINE;
        file += "1" + NEWLINE;
        file += singleParticipantLine + NEWLINE;

        return file;
    }
}

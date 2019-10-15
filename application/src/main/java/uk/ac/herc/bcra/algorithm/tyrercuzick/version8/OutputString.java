package uk.ac.herc.bcra.algorithm.tyrercuzick.version8;

import java.util.List;

import uk.ac.herc.bcra.algorithm.AlgorithmException;

public class OutputString {

    public static String getOutputString(List<Object> array) {
    
        String string = "";

        for (Object object: array) {

            if (object != array.get(0)) {
                string += "\t";
            }

            if (object == null) {
                throw new AlgorithmException("Cannot map NULL object to string: " + object);
            }
            else if (object instanceof String) {
                string += (String) object;
            }
            else if (object instanceof Integer) {
                string += (Integer) object;
            }
            else if (object instanceof Float) {
                Float decimal = (Float) object;
                string += String.format("%.2f", decimal);
            }
            else if (object instanceof Boolean) {
                if ((Boolean) object) {
                    string += "1";
                }
                else {
                    string += "0";
                }
            }
            else {
                throw new AlgorithmException("Cannot map object of unknown type: " + object);
            }
        }

        return string;
    }
}

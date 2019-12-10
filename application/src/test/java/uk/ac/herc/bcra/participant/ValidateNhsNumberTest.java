package uk.ac.herc.bcra.participant;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidateNhsNumberTest {

    @Test
    public void testValidNhsNumbers() throws Exception {

        ValidateNhsNumber validateNhsNumber = new ValidateNhsNumber();

        for (String nhsNumber: VALID_NHS_NUMBERS) {
            assertThat(
                validateNhsNumber.validate(nhsNumber)
            )
            .as("should be valid but fails: " + nhsNumber)
            .isNull();
        }
    }

    @Test
    public void testInvalidNhsNumbers() throws Exception {

        ValidateNhsNumber validateNhsNumber = new ValidateNhsNumber();

        for (String nhsNumber: INVALID_NHS_NUMBERS) {
            assertThat(
                validateNhsNumber.validate(nhsNumber)
            )
            .as("should be invalid but passes: " + nhsNumber)
            .startsWith("Invalid NHS Number");
        }
    }

    @Test
    public void testMalformedNhsNumbers() throws Exception {

        ValidateNhsNumber validateNhsNumber = new ValidateNhsNumber();

        for (String nhsNumber: MALFORMED_NHS_NUMBERS) {
            assertThat(
                validateNhsNumber.validate(nhsNumber)
            )
            .as("should be malformed but passes: " + nhsNumber)
            .startsWith("Bad NHS Number format");
        }
    }
    
    private final String[] VALID_NHS_NUMBERS = {
        "0589804693",
        "4158778468",
        "2210181747",
        "9654536706",
        "4733502672",
        "8261198545",
        "7940805592",
        "4062296462",
        "8846598881",
        "5392790852"
    };

    private final String[] INVALID_NHS_NUMBERS = {
        "2844821240",
        "9055990001",
        "6928055212",
        "9352083904",
        "7564809783",
        "8656081595",
        "0379951296",
        "9797109357",
        "3201826658",
        "6226049369"
    };

    private final String[] MALFORMED_NHS_NUMBERS = {
        "058 980 4693",
        "41587784682",
        "221018174",
        "96545367O6",
        "4733502672 ",
        "8261I98545",
        " 7940805592",
        "40622 96462",
        "884-659-8881",
        "null"
    };
}

package uk.ac.herc.bcra.participant;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidatePostcodeTest {

    @Test
    public void testValidPostcodes() throws Exception {

        ValidatePostcode validatePostcode = new ValidatePostcode();

        for (String postcode: VALID_POSTCODES) {
            assertThat(
                validatePostcode.validate(postcode)
            )
            .as("Should be valid but fails: " + postcode)
            .isNull();
        }
    }

    @Test
    public void testInvalidPostcodes() throws Exception {

        ValidatePostcode validatePostcode = new ValidatePostcode();

        for (String postcode: INVALID_POSTCODES) {
            assertThat(
                validatePostcode.validate(postcode)
            )
            .as("Should be invalid but it passes: " + postcode)
            .startsWith("Invalid postcode");
        }
    }

    private final String[] VALID_POSTCODES = {
        "EN6 1RD",
        "RH10 1SJ",
        "NG2 6RL",
        "KA18 3HT",
        "PH2 6HT",
        "HP3 8PU",
        "CV35 8SD",
        "SL6 7SB",
        "W3 8LQ",
        "CR0 5ES"
    };

    private final String[] INVALID_POSTCODES = {
        "CB6 22WW",
        "CA948SA",
        "PE111 1SV",
        "M61 99PL",
        "EXT17 3BQ",
        "CH48 4DVE",
        "236 9HU",
        "W5 9PQS",
        "Q000 2TJ",
        "LE2 453",
        "DI1 4WB",
        "null"
    };
}

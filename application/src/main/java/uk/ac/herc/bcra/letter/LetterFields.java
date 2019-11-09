package uk.ac.herc.bcra.letter;

public class LetterFields {

    private static final String HAIR_SPACE = String.valueOf((char) 0x200A);

    public static final String[] SENDER_ADDRESS =  new String[] {
        "",
        HAIR_SPACE + "                                    Family History Research Team",
        "Nightingale Centre and Genesis Prevention Centre",
        "         Manchester University NHS Foundation Trust",
        "                                                 Wythenshawe Hospital",
        "                                                                     Manchester",
        "                                                                           M23 9LT",
        "                                                         Tel: 0161 291 4409",
        HAIR_SPACE + "                                                        Fax: 0161 291 4421"
    };

    public static final Float SENDER_ADDRESS_OFFSET = 300f;

    public static final String[] SIGNER = new String[] {
        "Dr Sacha Howell",
        "",
        "Chief Investigator ALDRAM study"
    };

    public static final String SIGN_OFF= "Yours sincerely";
}

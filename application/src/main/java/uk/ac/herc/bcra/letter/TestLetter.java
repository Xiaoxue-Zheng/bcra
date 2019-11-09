package uk.ac.herc.bcra.letter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TestLetter {

    private final String LETTER_TYPE = "Invitation Letter";

    private final String LETTER_TITLE = "Short Title: Automated low dose risk assessment mammography (ALDRAM)";    

    private final String PARAGRAPH_ONE_TEXT = 
        "We are writing to tell you about a new research project which we would like you to consider joining. "
        + " Attached is a participant information sheet which gives you more information about the study.";

    private final Integer PARAGRAPH_ONE_ROW_COUNT = 3;

    private final String PARAGRAPH_TWO_TEXT = 
        "If you think you might be interested in taking part please tell the family history nurse or radiographer when you attend or your next appointment/mammogram. "
        + "If you would like to ask any questions about the study, please do not hesitate to contact the study team on 0161 291 4409. "
        + "If you do not wish to take part, this will not affect your treatment or relationship with staff within the clinic in any way.";

    private final Integer PARAGRAPH_TWO_ROW_COUNT = 5;

    private final String PARAGRAPH_THREE_TEXT = "Thank you for considering this study";

    private final Integer PARAGRAPH_THREE_ROW_COUNT = 1;
    
    private List<String> content;

    private Integer contentRowCount;

    public TestLetter() {
        content = new ArrayList<String>();
        content.add(PARAGRAPH_ONE_TEXT);
        content.add(PARAGRAPH_TWO_TEXT);
        content.add(PARAGRAPH_THREE_TEXT);

        contentRowCount = 
            PARAGRAPH_ONE_ROW_COUNT +
            PARAGRAPH_TWO_ROW_COUNT +
            PARAGRAPH_THREE_ROW_COUNT;
    }

    public byte[] generateLetter(
        String firstName,
        String lastName,
        List<String> address,
        String nhsNumber
    ) throws Exception {

        Letter letter = new Letter(
            firstName,
            lastName,
            address,
            nhsNumber,
            LETTER_TYPE,
            LETTER_TITLE,
            content,
            contentRowCount
        );

        return letter.generateLetter();
    }
}

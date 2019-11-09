package uk.ac.herc.bcra.letter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Letter {

    private LetterBase letterBase;

    private final Integer MAXIMUM_ADDRESS_ROWS = 5;
    private final Integer MAXIMUM_CONTENT_ROWS = 15;

    public Letter(
        String firstName,
        String lastName,
        List<String> address,
        String nhsNumber,
        String letterType,
        String title,
        List<String> content,
        Integer contentRows
    ) {
        if (address.size() > MAXIMUM_ADDRESS_ROWS) {
            throw new RuntimeException("TOO MANY ROWS IN ADDRESS" + address);
        }

        if (contentRows > MAXIMUM_CONTENT_ROWS) {
            throw new RuntimeException("TOO MANY ROWS IN CONTENT" + content);
        }

        String fullName = firstName + " " + lastName;
        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());;

        String[] introColumn = buildIntroColumn(date, address, fullName, nhsNumber);

        String[] titleSection = new String[] {
            letterType,
            "",
            title,
        };

        this.letterBase = new LetterBase(
            introColumn,
            titleSection,
            content,
            contentRows
        );
    }

    public byte[] generateLetter() throws Exception {
        return letterBase.generateLetter();
    }

    private String[] buildIntroColumn(
        String date,
        List<String> address,
        String fullName,
        String nhsNumber
    ) {
        List<String> intro = new ArrayList<String>();
        intro.add("Date: " + date);
        intro.add("");
        intro.add(fullName);
        intro.addAll(address);

        Integer extraRows = MAXIMUM_ADDRESS_ROWS - address.size();
        for (int eachRow = 0; eachRow < extraRows; eachRow++) {
            intro.add("");
        }

        intro.add("");
        intro.add("Dear " + fullName);

        return intro.toArray(new String[0]);
    }
}

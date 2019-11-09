package uk.ac.herc.bcra.letter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.pdfjet.*;

import org.springframework.core.io.ClassPathResource;

public class LetterBase {

    private Font regularFont;
    private Font boldFont;

    private String[] introColumn;
    private String[] titleSection;
    private List<String> content;
    private Float contentHeight;

    public LetterBase(
        String[] introColumn,
        String[] titleSection,
        List<String> content,
        Integer contentRowCount
    ) {
        this.introColumn = introColumn;
        this.titleSection = titleSection;

        List<String> fullContent = new ArrayList<String>();
        fullContent.addAll(content);
        fullContent.add(LetterFields.SIGN_OFF);
        contentRowCount++;

        Integer rowGaps = fullContent.size();
        contentRowCount += rowGaps;

        this.content = fullContent;
        this.contentHeight = (contentRowCount) * LetterConstants.CONTENT_ROW_SIZE;
    }

    public byte[] generateLetter() throws Exception {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PDF pdf = new PDF(byteArrayOutputStream, Compliance.PDF_UA);
        Page page = new Page(pdf, com.pdfjet.Letter.PORTRAIT);
        loadFonts(pdf);

        Float top = LetterConstants.MARGIN_TOP;

        drawLogos(pdf, page, top);
        top += LetterConstants.LOGO_HEIGHT;

        drawIntroColumn(pdf, page, top);
        drawSenderAddress(pdf, page, top);
        top += LetterConstants.INTRO_HEIGHT;

        drawTitle(pdf, page, top);
        top += LetterConstants.TITLE_HEIGHT;

        drawContent(pdf, page, top);
        top += contentHeight;

        drawSignature(pdf, page, top);
        top += LetterConstants.SIGNATURE_HEIGHT;

        drawSigner(pdf, page, top);

        pdf.close();

        return byteArrayOutputStream.toByteArray();
    }

    private void loadFonts(PDF pdf) throws Exception {
        regularFont = new Font(
            pdf,
            new ClassPathResource(LetterConstants.OPEN_SANS_REGULAR_FONT_FILE).getInputStream(),
            Font.STREAM
        );
        regularFont.setSize(LetterConstants.FONT_SIZE);

        boldFont = new Font(
            pdf,
            new ClassPathResource(LetterConstants.OPEN_SANS_BOLD_FONT_FILE).getInputStream(),
            Font.STREAM
        );
        boldFont.setSize(LetterConstants.FONT_SIZE);
    }

    private void drawLogos(PDF pdf, Page page, Float top) throws Exception {

        Image mrcLogo = new Image(
            pdf,
            new ClassPathResource(LetterConstants.MRC_LOGO_FILE).getInputStream(),
            ImageType.PNG
        );
        mrcLogo.setLocation(LetterConstants.MARGIN_LEFT, top);
        mrcLogo.scaleBy(LetterConstants.MRC_LOGO_SCALE);
        mrcLogo.drawOn(page);

        Image nhsLogo = new Image(
            pdf,
            new ClassPathResource(LetterConstants.NHS_LOGO_FILE).getInputStream(),
            ImageType.PNG
        );  
        nhsLogo.setLocation(LetterConstants.NHS_LOGO_OFFSET, top);
        nhsLogo.scaleBy(LetterConstants.NHS_LOGO_SCALE);
        nhsLogo.drawOn(page);
    }

    private void drawIntroColumn(PDF pdf, Page page, Float top) throws Exception {
        new PlainText(regularFont, introColumn)
            .setLocation(LetterConstants.MARGIN_LEFT, top)
            .drawOn(page);
    }

    private void drawSenderAddress(PDF pdf, Page page, Float top) throws Exception {
        new PlainText(regularFont, LetterFields.SENDER_ADDRESS)
            .setLocation(LetterFields.SENDER_ADDRESS_OFFSET, top)
            .drawOn(page);
    }

    private void drawTitle(PDF pdf, Page page, Float top) throws Exception {
        new PlainText(boldFont, titleSection)
            .setLocation(LetterConstants.MARGIN_LEFT, top)
            .drawOn(page);     
    }

    private void drawContent(PDF pdf, Page page, Float top) throws Exception {
        List<Paragraph> paragraphs = new ArrayList<Paragraph>();
        for (String row: content) {
            paragraphs.add(
                new Paragraph()
                .add(new TextLine(regularFont, row))
            );
        }

        Text textArea = new Text(paragraphs);
        textArea.setLocation(LetterConstants.MARGIN_LEFT, top);
        textArea.setWidth(LetterConstants.PAGE_WIDTH);
        textArea.drawOn(page);
    }

    private void drawSignature(PDF pdf, Page page, Float top) throws Exception {
        Image signature = new Image(
            pdf,
            new ClassPathResource(LetterConstants.SIGNATURE_FILE).getInputStream(),
            ImageType.PNG
        );

        signature.setLocation(LetterConstants.MARGIN_LEFT, top);
        signature.scaleBy(LetterConstants.SIGNATURE_SCALE);
        signature.drawOn(page);
    }

    private void drawSigner(PDF pdf, Page page, Float top) throws Exception {
        new PlainText(regularFont, LetterFields.SIGNER)
            .setLocation(LetterConstants.MARGIN_LEFT, top)
            .drawOn(page);
    }
}

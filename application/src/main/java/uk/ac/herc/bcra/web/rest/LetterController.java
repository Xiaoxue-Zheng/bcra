package uk.ac.herc.bcra.web.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import uk.ac.herc.bcra.letter.TestLetter;


@RestController
@RequestMapping("/api")
public class LetterController {

    private final Logger log = LoggerFactory.getLogger(LetterController.class);

    private TestLetter testLetter;

    public LetterController(TestLetter testLetter) {
      this.testLetter = testLetter;
    }

    @GetMapping(value = "/letter", produces = MediaType.APPLICATION_PDF_VALUE)
      public @ResponseBody byte[] getFile() throws Exception {
        log.debug("REST request to get a letter");

        List<String> address = new ArrayList<String>();
        address.add("123 Higher Road");
        address.add("Blickling");
        address.add("Norwich");
        address.add("NR11 4EK");

          return testLetter.generateLetter(
            "Jane",
            "Smith",
            address,
            "675 564 9481"
          );
      }
}

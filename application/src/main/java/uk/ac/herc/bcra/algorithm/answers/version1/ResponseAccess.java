package uk.ac.herc.bcra.algorithm.answers.version1;

import java.util.HashMap;
import java.util.Map;

import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.AnswerSection;
import uk.ac.herc.bcra.domain.enumeration.QuestionSectionIdentifier;

public class ResponseAccess {

    private Map<QuestionSectionIdentifier, SectionAccess> sectionMap
        = new HashMap<QuestionSectionIdentifier, SectionAccess>();

    public ResponseAccess(AnswerResponse response) {
        super();

        for (AnswerSection section: response.getAnswerSections()) {
            sectionMap.put(
                section.getQuestionSection().getIdentifier(),
                new SectionAccess(section)
            );
        }
    }

    public SectionAccess section(QuestionSectionIdentifier identifier) {
        return sectionMap.get(identifier);
    }
}


package uk.ac.herc.bcra.algorithm.answers.version1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.ac.herc.bcra.algorithm.AlgorithmException;
import uk.ac.herc.bcra.domain.AnswerGroup;
import uk.ac.herc.bcra.domain.AnswerSection;
import uk.ac.herc.bcra.domain.enumeration.QuestionSectionIdentifier;

public class SectionAccess {

    public List<GroupAccess> groups = new ArrayList<GroupAccess>();

    private QuestionSectionIdentifier sectionIdentifier;

    public SectionAccess(AnswerSection section) {
        super();

        for (AnswerGroup group: section.getAnswerGroups()) {
            groups.add(new GroupAccess(group));
        }

        Collections.sort(groups);

        sectionIdentifier = section.getQuestionSection().getIdentifier();
    }

    public GroupAccess getOnlyGroup() {
        if (groups.size() == 1) {
            return groups.get(0);
        }
        else {
            throw new AlgorithmException(
                "More than one group in section "
                + sectionIdentifier
                + ": "
                + groups
            );
        }
    }
}
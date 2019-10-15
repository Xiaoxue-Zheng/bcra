package uk.ac.herc.bcra.algorithm.mapping.answers1_tyrercuzick8;

import java.time.LocalDate;

import uk.ac.herc.bcra.algorithm.answers.version1.GroupAccess;
import uk.ac.herc.bcra.algorithm.answers.version1.ResponseAccess;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.AlgorithmModel;
import uk.ac.herc.bcra.algorithm.tyrercuzick.version8.OutputFile;
import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.domain.enumeration.QuestionSectionIdentifier;

public class Mapper {

    public String map(String participantIdentifier, LocalDate dateOfBirth, AnswerResponse answerResponse) {

        ResponseAccess response = new ResponseAccess(answerResponse);

        AlgorithmModel algorithm = new AlgorithmModel(participantIdentifier);
        algorithm.setDateOfBirth(dateOfBirth);

        GroupAccess personalHistoryGroup = response.section(QuestionSectionIdentifier.PERSONAL_HISTORY).getOnlyGroup();
        SelfMapper.map(personalHistoryGroup, algorithm.self);

        // Family History
        RelativeMapper.map(response, algorithm);        

        // Other Fields (male relatives, etc.)
        GroupAccess maleRelativeGroup = response.section(QuestionSectionIdentifier.MALE_BREAST_CANCER).getOnlyGroup();
        MaleMapper.map(maleRelativeGroup, algorithm.father, algorithm.brother);

        return OutputFile.mapAlgorithmToFile(algorithm);
    }
}
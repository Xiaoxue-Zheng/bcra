package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.QuestionDTO;
import uk.ac.herc.bcra.service.dto.QuestionGroupDTO;

import java.util.Set;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link QuestionGroup} and its DTO {@link QuestionGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuestionGroupMapper extends EntityMapper<QuestionGroupDTO, QuestionGroup> {

    Set<QuestionDTO> questionsToQuestionDTOs(Set<Question> questions);

    @Mapping(target = "questionSections", ignore = true)
    @Mapping(target = "removeQuestionSection", ignore = true)
    @Mapping(target = "questions", ignore = true)
    @Mapping(target = "removeQuestion", ignore = true)
    QuestionGroup toEntity(QuestionGroupDTO questionGroupDTO);

    default QuestionGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        QuestionGroup questionGroup = new QuestionGroup();
        questionGroup.setId(id);
        return questionGroup;
    }
}

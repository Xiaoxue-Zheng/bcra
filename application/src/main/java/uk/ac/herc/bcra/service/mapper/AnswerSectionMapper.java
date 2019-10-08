package uk.ac.herc.bcra.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import uk.ac.herc.bcra.domain.AnswerSection;
import uk.ac.herc.bcra.service.dto.AnswerSectionDTO;

/**
 * Mapper for the entity {@link AnswerSection} and its DTO {@link AnswerSectionDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnswerResponseMapper.class, QuestionSectionMapper.class, AnswerGroupMapper.class})
public interface AnswerSectionMapper extends EntityMapper<AnswerSectionDTO, AnswerSection> {

    @Mapping(source = "answerResponse.id", target = "answerResponseId")
    @Mapping(source = "questionSection.id", target = "questionSectionId")
    AnswerSectionDTO toDto(AnswerSection answerSection);

    @Mapping(source = "answerResponseId", target = "answerResponse")
    @Mapping(source = "questionSectionId", target = "questionSection")
    @Mapping(source = "answerGroups", target = "answerGroups")
    @Mapping(target = "removeAnswerGroup", ignore = true)
    AnswerSection toEntity(AnswerSectionDTO answerSectionDTO);

    default AnswerSection fromId(Long id) {
        if (id == null) {
            return null;
        }
        AnswerSection answerSection = new AnswerSection();
        answerSection.setId(id);
        return answerSection;
    }
}

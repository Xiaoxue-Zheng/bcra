package uk.ac.herc.bcra.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import uk.ac.herc.bcra.domain.AnswerResponse;
import uk.ac.herc.bcra.service.dto.AnswerResponseDTO;

/**
 * Mapper for the entity {@link AnswerResponse} and its DTO {@link AnswerResponseDTO}.
 */
@Mapper(componentModel = "spring", uses = {QuestionnaireMapper.class, AnswerSectionMapper.class})
public interface AnswerResponseMapper extends EntityMapper<AnswerResponseDTO, AnswerResponse> {

    @Mapping(source = "questionnaire.id", target = "questionnaireId")
    AnswerResponseDTO toDto(AnswerResponse answerResponse);

    @Mapping(source = "questionnaireId", target = "questionnaire")
    @Mapping(source = "answerSections", target="answerSections")
    @Mapping(target = "removeAnswerSection", ignore = true)
    AnswerResponse toEntity(AnswerResponseDTO answerResponseDTO);

    default AnswerResponse fromId(Long id) {
        if (id == null) {
            return null;
        }
        AnswerResponse answerResponse = new AnswerResponse();
        answerResponse.setId(id);
        return answerResponse;
    }
}

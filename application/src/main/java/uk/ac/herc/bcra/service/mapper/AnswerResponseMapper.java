package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.AnswerResponseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnswerResponse} and its DTO {@link AnswerResponseDTO}.
 */
@Mapper(componentModel = "spring", uses = {QuestionnaireMapper.class, AnswerGroupMapper.class})
public interface AnswerResponseMapper extends EntityMapper<AnswerResponseDTO, AnswerResponse> {

    @Mapping(source = "questionnaire.id", target = "questionnaireId")
    AnswerResponseDTO toDto(AnswerResponse answerResponse);

    @Mapping(source = "questionnaireId", target = "questionnaire")
    @Mapping(source = "answerGroups", target = "answerGroups")
    @Mapping(target = "removeAnswerGroup", ignore = true)
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

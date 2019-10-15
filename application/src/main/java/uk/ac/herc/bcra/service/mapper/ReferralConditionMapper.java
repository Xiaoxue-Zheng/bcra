package uk.ac.herc.bcra.service.mapper;

import uk.ac.herc.bcra.domain.*;
import uk.ac.herc.bcra.service.dto.ReferralConditionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReferralCondition} and its DTO {@link ReferralConditionDTO}.
 */
@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface ReferralConditionMapper extends EntityMapper<ReferralConditionDTO, ReferralCondition> {

    ReferralConditionDTO toDto(ReferralCondition referralCondition);

    ReferralCondition toEntity(ReferralConditionDTO referralConditionDTO);

    default ReferralCondition fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReferralCondition referralCondition = new ReferralCondition();
        referralCondition.setId(id);
        return referralCondition;
    }
}

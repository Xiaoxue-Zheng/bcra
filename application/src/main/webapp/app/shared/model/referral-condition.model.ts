import { ReferralConditionType } from 'app/shared/model/enumerations/referral-condition-type.model';
import { QuestionIdentifier } from 'app/shared/model/enumerations/question-identifier.model';
import { QuestionItemIdentifier } from 'app/shared/model/enumerations/question-item-identifier.model';

export interface IReferralCondition {
  id?: number;
  andGroup?: number;
  type?: ReferralConditionType;
  questionIdentifier?: QuestionIdentifier;
  itemIdentifier?: QuestionItemIdentifier;
  number?: number;
  reason?: string;
  questionSectionId?: number;
}

export class ReferralCondition implements IReferralCondition {
  constructor(
    public id?: number,
    public andGroup?: number,
    public type?: ReferralConditionType,
    public questionIdentifier?: QuestionIdentifier,
    public itemIdentifier?: QuestionItemIdentifier,
    public number?: number,
    public reason?: string,
    public questionSectionId?: number
  ) {}
}

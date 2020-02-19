import { IDisplayCondition } from 'app/shared/model/display-condition.model';
import { IReferralCondition } from 'app/shared/model/referral-condition.model';
import { QuestionSectionIdentifier } from 'app/shared/model/enumerations/question-section-identifier.model';

export interface IQuestionSection {
  id?: number;
  identifier?: QuestionSectionIdentifier;
  title?: string;
  order?: number;
  url?: string;
  progress?: number;
  displayConditions?: IDisplayCondition[];
  referralConditions?: IReferralCondition[];
  questionnaireId?: number;
  questionGroupId?: number;
}

export class QuestionSection implements IQuestionSection {
  constructor(
    public id?: number,
    public identifier?: QuestionSectionIdentifier,
    public title?: string,
    public order?: number,
    public url?: string,
    public progress?: number,
    public displayConditions?: IDisplayCondition[],
    public referralConditions?: IReferralCondition[],
    public questionnaireId?: number,
    public questionGroupId?: number
  ) {}
}

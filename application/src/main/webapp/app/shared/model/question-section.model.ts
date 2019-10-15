import { IDisplayCondition } from 'app/shared/model/display-condition.model';

export const enum QuestionSectionIdentifier {
  CONSENT_FORM = 'CONSENT_FORM',
  FAMILY_BREAST_AFFECTED = 'FAMILY_BREAST_AFFECTED',
  FAMILY_BREAST_HOW_MANY = 'FAMILY_BREAST_HOW_MANY',
  FAMILY_BREAST_AGE = 'FAMILY_BREAST_AGE',
  FAMILY_OVARIAN_AFFECTED = 'FAMILY_OVARIAN_AFFECTED',
  FAMILY_OVARIAN_HOW_MANY = 'FAMILY_OVARIAN_HOW_MANY',
  FAMILY_OVARIAN_AGE = 'FAMILY_OVARIAN_AGE',
  FAMILY_AFFECTED_GRANDMOTHER = 'FAMILY_AFFECTED_GRANDMOTHER',
  FAMILY_AFFECTED_AUNT = 'FAMILY_AFFECTED_AUNT',
  FAMILY_AFFECTED_NIECE = 'FAMILY_AFFECTED_NIECE',
  FAMILY_AFFECTED_HALF_SISTER = 'FAMILY_AFFECTED_HALF_SISTER',
  PERSONAL_HISTORY = 'PERSONAL_HISTORY',
  MALE_BREAST_CANCER = 'MALE_BREAST_CANCER'
}

export interface IQuestionSection {
  id?: number;
  identifier?: QuestionSectionIdentifier;
  order?: number;
  displayConditions?: IDisplayCondition[];
  questionnaireId?: number;
  questionGroupId?: number;
}

export class QuestionSection implements IQuestionSection {
  constructor(
    public id?: number,
    public identifier?: QuestionSectionIdentifier,
    public order?: number,
    public displayConditions?: IDisplayCondition[],
    public questionnaireId?: number,
    public questionGroupId?: number
  ) {}
}

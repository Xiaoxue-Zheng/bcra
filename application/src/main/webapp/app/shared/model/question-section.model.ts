import { IDisplayCondition } from 'app/shared/model/display-condition.model';

export const enum QuestionSectionIdentifier {
  PERSONAL_HISTORY = 'PERSONAL_HISTORY',
  FAMILY_HISTORY = 'FAMILY_HISTORY',
  MOTHER = 'MOTHER',
  SISTER_COUNT = 'SISTER_COUNT',
  SISTERS = 'SISTERS',
  NIECE_COUNT = 'NIECE_COUNT',
  NIECES = 'NIECES',
  MATERNAL_HALF_SISTER_COUNT = 'MATERNAL_HALF_SISTER_COUNT',
  MATERNAL_HALF_SISTERS = 'MATERNAL_HALF_SISTERS',
  MATERNAL_GRANDMOTHER = 'MATERNAL_GRANDMOTHER',
  MATERNAL_AUNT_COUNT = 'MATERNAL_AUNT_COUNT',
  MATERNAL_AUNTS = 'MATERNAL_AUNTS',
  MATERNAL_COUSIN_COUNT = 'MATERNAL_COUSIN_COUNT',
  MATERNAL_COUSINS = 'MATERNAL_COUSINS',
  PATERNAL_HALF_SISTER_COUNT = 'PATERNAL_HALF_SISTER_COUNT',
  PATERNAL_HALF_SISTERS = 'PATERNAL_HALF_SISTERS',
  PATERNAL_GRANDMOTHER = 'PATERNAL_GRANDMOTHER',
  PATERNAL_AUNT_COUNT = 'PATERNAL_AUNT_COUNT',
  PATERNAL_AUNTS = 'PATERNAL_AUNTS',
  PATERNAL_COUSIN_COUNT = 'PATERNAL_COUSIN_COUNT',
  PATERNAL_COUSINS = 'PATERNAL_COUSINS',
  MALE_BREAST_CANCER = 'MALE_BREAST_CANCER'
}

export interface IQuestionSection {
  id?: number;
  identifier?: QuestionSectionIdentifier;
  order?: number;
  relativeName?: string;
  displayConditions?: IDisplayCondition[];
  questionnaireId?: number;
  questionGroupId?: number;
}

export class QuestionSection implements IQuestionSection {
  constructor(
    public id?: number,
    public identifier?: QuestionSectionIdentifier,
    public order?: number,
    public relativeName?: string,
    public displayConditions?: IDisplayCondition[],
    public questionnaireId?: number,
    public questionGroupId?: number
  ) {}
}

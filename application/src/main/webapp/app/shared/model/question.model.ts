import { IDisplayCondition } from 'app/shared/model/display-condition.model';
import { IQuestionItem } from 'app/shared/model/question-item.model';

export const enum QuestionIdentifier {
  SELF_FIRST_PERIOD = 'SELF_FIRST_PERIOD',
  SELF_PREMENOPAUSAL = 'SELF_PREMENOPAUSAL',
  SELF_MENOPAUSAL_AGE = 'SELF_MENOPAUSAL_AGE',
  SELF_PREGNANCIES = 'SELF_PREGNANCIES',
  SELF_PREGNANCY_FIRST_AGE = 'SELF_PREGNANCY_FIRST_AGE',
  SELF_HEIGHT = 'SELF_HEIGHT',
  SELF_WEIGHT = 'SELF_WEIGHT',
  SELF_BREAST_BIOPSY = 'SELF_BREAST_BIOPSY',
  SELF_BREAST_BIOPSY_DIAGNOSIS_RISK = 'SELF_BREAST_BIOPSY_DIAGNOSIS_RISK',
  SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES = 'SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES',
  SELF_ASHKENAZI = 'SELF_ASHKENAZI',
  FAMILY_BLOOD_RELATIVES = 'FAMILY_BLOOD_RELATIVES',
  FAMILY_MATERNAL_RELATIVES = 'FAMILY_MATERNAL_RELATIVES',
  FAMILY_PATERNAL_RELATIVES = 'FAMILY_PATERNAL_RELATIVES',
  RELATIVE_HOW_MANY = 'RELATIVE_HOW_MANY',
  RELATIVE_DIAGNOSIS = 'RELATIVE_DIAGNOSIS',
  RELATIVE_FIRST_DIAGNOSIS_AGE = 'RELATIVE_FIRST_DIAGNOSIS_AGE',
  RELATIVE_SECOND_DIAGNOSIS_AGE = 'RELATIVE_SECOND_DIAGNOSIS_AGE',
  RELATIVE_GENETIC_TESTING = 'RELATIVE_GENETIC_TESTING',
  RELATIVE_GENETIC_TESTING_RESULT = 'RELATIVE_GENETIC_TESTING_RESULT',
  RELATIVE_OVARIAN_CANCER = 'RELATIVE_OVARIAN_CANCER',
  RELATIVE_OVARIAN_CANCER_AGE = 'RELATIVE_OVARIAN_CANCER_AGE',
  FATHER_BREAST_CANCER = 'FATHER_BREAST_CANCER',
  BROTHER_BREAST_CANCER = 'BROTHER_BREAST_CANCER'
}

export const enum QuestionType {
  NUMBER = 'NUMBER',
  NUMBER_WEIGHT = 'NUMBER_WEIGHT',
  NUMBER_HEIGHT = 'NUMBER_HEIGHT',
  NUMBER_UNKNOWN = 'NUMBER_UNKNOWN',
  DROPDOWN_NUMBER = 'DROPDOWN_NUMBER',
  CHECKBOX = 'CHECKBOX',
  RADIO = 'RADIO'
}

export interface IQuestion {
  id?: number;
  identifier?: QuestionIdentifier;
  type?: QuestionType;
  order?: number;
  text?: string;
  minimum?: number;
  maximum?: number;
  displayConditions?: IDisplayCondition[];
  questionGroupId?: number;
  questionItems?: IQuestionItem[];
}

export class Question implements IQuestion {
  constructor(
    public id?: number,
    public identifier?: QuestionIdentifier,
    public type?: QuestionType,
    public order?: number,
    public text?: string,
    public minimum?: number,
    public maximum?: number,
    public displayConditions?: IDisplayCondition[],
    public questionGroupId?: number,
    public questionItems?: IQuestionItem[]
  ) {}
}

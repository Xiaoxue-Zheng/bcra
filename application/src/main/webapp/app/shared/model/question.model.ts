import { IDisplayCondition } from 'app/shared/model/display-condition.model';
import { IReferralCondition } from 'app/shared/model/referral-condition.model';
import { IQuestionItem } from 'app/shared/model/question-item.model';

export const enum QuestionIdentifier {
  CONSENT_INFO_SHEET = 'CONSENT_INFO_SHEET',
  CONSENT_WITHDRAWAL = 'CONSENT_WITHDRAWAL',
  CONSENT_DATA_TRUST = 'CONSENT_DATA_TRUST',
  CONSENT_DATA_RESEARCH = 'CONSENT_DATA_RESEARCH',
  CONSENT_DATA_UOM = 'CONSENT_DATA_UOM',
  CONSENT_DATA_COMMERCIAL = 'CONSENT_DATA_COMMERCIAL',
  CONSENT_INFORM_GP = 'CONSENT_INFORM_GP',
  CONSENT_TAKE_PART = 'CONSENT_TAKE_PART',
  CONSENT_BY_LETTER = 'CONSENT_BY_LETTER',
  CONSENT_FUTURE_RESEARCH = 'CONSENT_FUTURE_RESEARCH',
  FAMILY_BREAST_AFFECTED = 'FAMILY_BREAST_AFFECTED',
  FAMILY_BREAST_HOW_MANY = 'FAMILY_BREAST_HOW_MANY',
  FAMILY_BREAST_AGE = 'FAMILY_BREAST_AGE',
  FAMILY_OVARIAN_AFFECTED = 'FAMILY_OVARIAN_AFFECTED',
  FAMILY_OVARIAN_HOW_MANY = 'FAMILY_OVARIAN_HOW_MANY',
  FAMILY_OVARIAN_AGE = 'FAMILY_OVARIAN_AGE',
  FAMILY_AFFECTED_GRANDMOTHER_SIDE = 'FAMILY_AFFECTED_GRANDMOTHER_SIDE',
  FAMILY_AFFECTED_GRANDMOTHER_MOTHERS_AGE = 'FAMILY_AFFECTED_GRANDMOTHER_MOTHERS_AGE',
  FAMILY_AFFECTED_AUNT_SIDE = 'FAMILY_AFFECTED_AUNT_SIDE',
  FAMILY_AFFECTED_AUNT_MOTHERS_AGE = 'FAMILY_AFFECTED_AUNT_MOTHERS_AGE',
  FAMILY_AFFECTED_NIECE_SIDE = 'FAMILY_AFFECTED_NIECE_SIDE',
  FAMILY_AFFECTED_NIECE_SISTERS_AGE = 'FAMILY_AFFECTED_NIECE_SISTERS_AGE',
  FAMILY_AFFECTED_HALFSISTER_SIDE = 'FAMILY_AFFECTED_HALFSISTER_SIDE',
  FAMILY_AFFECTED_HALFSISTER_MOTHERS_AGE = 'FAMILY_AFFECTED_HALFSISTER_MOTHERS_AGE',
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
  FATHER_BREAST_CANCER = 'FATHER_BREAST_CANCER',
  BROTHER_BREAST_CANCER = 'BROTHER_BREAST_CANCER'
}

export const enum QuestionType {
  TICKBOX_CONSENT = 'TICKBOX_CONSENT',
  CHECKBOX = 'CHECKBOX',
  RADIO = 'RADIO',
  NUMBER = 'NUMBER',
  NUMBER_UNKNOWN = 'NUMBER_UNKNOWN',
  NUMBER_WEIGHT = 'NUMBER_WEIGHT',
  NUMBER_HEIGHT = 'NUMBER_HEIGHT',
  DROPDOWN_NUMBER = 'DROPDOWN_NUMBER'
}

export interface IQuestion {
  id?: number;
  identifier?: QuestionIdentifier;
  type?: QuestionType;
  order?: number;
  text?: string;
  variableName?: string;
  minimum?: number;
  maximum?: number;
  hint?: string;
  hintText?: string;
  displayConditions?: IDisplayCondition[];
  referralConditions?: IReferralCondition[];
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
    public variableName?: string,
    public minimum?: number,
    public maximum?: number,
    public hint?: string,
    public hintText?: string,
    public displayConditions?: IDisplayCondition[],
    public referralConditions?: IReferralCondition[],
    public questionGroupId?: number,
    public questionItems?: IQuestionItem[]
  ) {}
}

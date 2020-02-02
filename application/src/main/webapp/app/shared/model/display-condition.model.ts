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

export const enum QuestionItemIdentifier {
  CONSENT_INFO_SHEET_YES = 'CONSENT_INFO_SHEET_YES',
  CONSENT_INFO_SHEET_NO = 'CONSENT_INFO_SHEET_NO',
  CONSENT_FUTURE_RESEARCH_YES = 'CONSENT_FUTURE_RESEARCH_YES',
  CONSENT_FUTURE_RESEARCH_NO = 'CONSENT_FUTURE_RESEARCH_NO',
  FAMILY_BREAST_AFFECTED_MOTHER = 'FAMILY_BREAST_AFFECTED_MOTHER',
  FAMILY_BREAST_AFFECTED_GRANDMOTHER = 'FAMILY_BREAST_AFFECTED_GRANDMOTHER',
  FAMILY_BREAST_AFFECTED_SISTER = 'FAMILY_BREAST_AFFECTED_SISTER',
  FAMILY_BREAST_AFFECTED_HALFSISTER = 'FAMILY_BREAST_AFFECTED_HALFSISTER',
  FAMILY_BREAST_AFFECTED_AUNT = 'FAMILY_BREAST_AFFECTED_AUNT',
  FAMILY_BREAST_AFFECTED_NIECE = 'FAMILY_BREAST_AFFECTED_NIECE',
  FAMILY_BREAST_AFFECTED_NONE = 'FAMILY_BREAST_AFFECTED_NONE',
  FAMILY_BREAST_HOW_MANY_ONE = 'FAMILY_BREAST_HOW_MANY_ONE',
  FAMILY_BREAST_HOW_MANY_MORE = 'FAMILY_BREAST_HOW_MANY_MORE',
  FAMILY_OVARIAN_AFFECTED_MOTHER = 'FAMILY_OVARIAN_AFFECTED_MOTHER',
  FAMILY_OVARIAN_AFFECTED_GRANDMOTHER = 'FAMILY_OVARIAN_AFFECTED_GRANDMOTHER',
  FAMILY_OVARIAN_AFFECTED_SISTER = 'FAMILY_OVARIAN_AFFECTED_SISTER',
  FAMILY_OVARIAN_AFFECTED_HALFSISTER = 'FAMILY_OVARIAN_AFFECTED_HALFSISTER',
  FAMILY_OVARIAN_AFFECTED_AUNT = 'FAMILY_OVARIAN_AFFECTED_AUNT',
  FAMILY_OVARIAN_AFFECTED_NIECE = 'FAMILY_OVARIAN_AFFECTED_NIECE',
  FAMILY_OVARIAN_AFFECTED_NONE = 'FAMILY_OVARIAN_AFFECTED_NONE',
  FAMILY_OVARIAN_HOW_MANY_ONE = 'FAMILY_OVARIAN_HOW_MANY_ONE',
  FAMILY_OVARIAN_HOW_MANY_MORE = 'FAMILY_OVARIAN_HOW_MANY_MORE',
  FAMILY_AFFECTED_GRANDMOTHER_SIDE_MOTHER = 'FAMILY_AFFECTED_GRANDMOTHER_SIDE_MOTHER',
  FAMILY_AFFECTED_GRANDMOTHER_SIDE_FATHER = 'FAMILY_AFFECTED_GRANDMOTHER_SIDE_FATHER',
  FAMILY_AFFECTED_AUNT_SIDE_MOTHER = 'FAMILY_AFFECTED_AUNT_SIDE_MOTHER',
  FAMILY_AFFECTED_AUNT_SIDE_FATHER = 'FAMILY_AFFECTED_AUNT_SIDE_FATHER',
  FAMILY_AFFECTED_NIECE_SIDE_SISTER = 'FAMILY_AFFECTED_NIECE_SIDE_SISTER',
  FAMILY_AFFECTED_NIECE_SIDE_BROTHER = 'FAMILY_AFFECTED_NIECE_SIDE_BROTHER',
  FAMILY_AFFECTED_HALFSISTER_SIDE_MOTHER = 'FAMILY_AFFECTED_HALFSISTER_SIDE_MOTHER',
  FAMILY_AFFECTED_HALFSISTER_SIDE_FATHER = 'FAMILY_AFFECTED_HALFSISTER_SIDE_FATHER',
  SELF_PREMENOPAUSAL_YES = 'SELF_PREMENOPAUSAL_YES',
  SELF_PREMENOPAUSAL_NO = 'SELF_PREMENOPAUSAL_NO',
  SELF_PREMENOPAUSAL_UNKNOWN = 'SELF_PREMENOPAUSAL_UNKNOWN',
  SELF_BREAST_BIOPSY_YES = 'SELF_BREAST_BIOPSY_YES',
  SELF_BREAST_BIOPSY_NO = 'SELF_BREAST_BIOPSY_NO',
  SELF_BREAST_BIOPSY_UNKNOWN = 'SELF_BREAST_BIOPSY_UNKNOWN',
  SELF_BREAST_BIOPSY_DIAGNOSIS_RISK_YES = 'SELF_BREAST_BIOPSY_DIAGNOSIS_RISK_YES',
  SELF_BREAST_BIOPSY_DIAGNOSIS_RISK_NO = 'SELF_BREAST_BIOPSY_DIAGNOSIS_RISK_NO',
  SELF_BREAST_BIOPSY_DIAGNOSIS_RISK_UNKNOWN = 'SELF_BREAST_BIOPSY_DIAGNOSIS_RISK_UNKNOWN',
  SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_ADH = 'SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_ADH',
  SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_LCIS = 'SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_LCIS',
  SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_ALH = 'SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_ALH',
  SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_OTHER = 'SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_OTHER',
  SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_UNKNOWN = 'SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_UNKNOWN',
  SELF_ASHKENAZI_YES = 'SELF_ASHKENAZI_YES',
  SELF_ASHKENAZI_NO = 'SELF_ASHKENAZI_NO',
  SELF_ASHKENAZI_UNKNOWN = 'SELF_ASHKENAZI_UNKNOWN',
  FATHER_BREAST_CANCER_YES = 'FATHER_BREAST_CANCER_YES',
  FATHER_BREAST_CANCER_NO = 'FATHER_BREAST_CANCER_NO',
  BROTHER_BREAST_CANCER_YES = 'BROTHER_BREAST_CANCER_YES',
  BROTHER_BREAST_CANCER_NO = 'BROTHER_BREAST_CANCER_NO'
}

export interface IDisplayCondition {
  id?: number;
  questionIdentifier?: QuestionIdentifier;
  itemIdentifier?: QuestionItemIdentifier;
  questionSectionId?: number;
  questionId?: number;
}

export class DisplayCondition implements IDisplayCondition {
  constructor(
    public id?: number,
    public questionIdentifier?: QuestionIdentifier,
    public itemIdentifier?: QuestionItemIdentifier,
    public questionSectionId?: number,
    public questionId?: number
  ) {}
}

export const enum QuestionItemIdentifier {
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
  SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_FORGOT = 'SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_FORGOT',
  SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_UNKNOWN = 'SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_UNKNOWN',
  SELF_ASHKENAZI_YES = 'SELF_ASHKENAZI_YES',
  SELF_ASHKENAZI_NO = 'SELF_ASHKENAZI_NO',
  SELF_ASHKENAZI_UNKNOWN = 'SELF_ASHKENAZI_UNKNOWN',
  FAMILY_BLOOD_RELATIVES_MOTHER = 'FAMILY_BLOOD_RELATIVES_MOTHER',
  FAMILY_BLOOD_RELATIVES_SISTERS = 'FAMILY_BLOOD_RELATIVES_SISTERS',
  FAMILY_BLOOD_RELATIVES_NIECES = 'FAMILY_BLOOD_RELATIVES_NIECES',
  FAMILY_MATERNAL_RELATIVES_HALF_SISTERS = 'FAMILY_MATERNAL_RELATIVES_HALF_SISTERS',
  FAMILY_MATERNAL_RELATIVES_GRANDMOTHER = 'FAMILY_MATERNAL_RELATIVES_GRANDMOTHER',
  FAMILY_MATERNAL_RELATIVES_AUNTS = 'FAMILY_MATERNAL_RELATIVES_AUNTS',
  FAMILY_MATERNAL_RELATIVES_COUSINS = 'FAMILY_MATERNAL_RELATIVES_COUSINS',
  FAMILY_PATERNAL_RELATIVES_HALF_SISTERS = 'FAMILY_PATERNAL_RELATIVES_HALF_SISTERS',
  FAMILY_PATERNAL_RELATIVES_GRANDMOTHER = 'FAMILY_PATERNAL_RELATIVES_GRANDMOTHER',
  FAMILY_PATERNAL_RELATIVES_AUNTS = 'FAMILY_PATERNAL_RELATIVES_AUNTS',
  FAMILY_PATERNAL_RELATIVES_COUSINS = 'FAMILY_PATERNAL_RELATIVES_COUSINS',
  RELATIVE_DIAGNOSIS_UNILATERAL = 'RELATIVE_DIAGNOSIS_UNILATERAL',
  RELATIVE_DIAGNOSIS_BILATERAL = 'RELATIVE_DIAGNOSIS_BILATERAL',
  RELATIVE_DIAGNOSIS_UNKNOWN = 'RELATIVE_DIAGNOSIS_UNKNOWN',
  RELATIVE_GENETIC_TESTING_YES = 'RELATIVE_GENETIC_TESTING_YES',
  RELATIVE_GENETIC_TESTING_NO = 'RELATIVE_GENETIC_TESTING_NO',
  RELATIVE_GENETIC_TESTING_UNKNOWN = 'RELATIVE_GENETIC_TESTING_UNKNOWN',
  RELATIVE_GENETIC_TESTING_RESULT_BRCA1 = 'RELATIVE_GENETIC_TESTING_RESULT_BRCA1',
  RELATIVE_GENETIC_TESTING_RESULT_BRCA2 = 'RELATIVE_GENETIC_TESTING_RESULT_BRCA2',
  RELATIVE_GENETIC_TESTING_RESULT_NONE = 'RELATIVE_GENETIC_TESTING_RESULT_NONE',
  RELATIVE_GENETIC_TESTING_RESULT_UNKNOWN = 'RELATIVE_GENETIC_TESTING_RESULT_UNKNOWN'
}

export interface IQuestionItem {
  id?: number;
  identifier?: QuestionItemIdentifier;
  order?: number;
  label?: string;
  questionId?: number;
}

export class QuestionItem implements IQuestionItem {
  constructor(
    public id?: number,
    public identifier?: QuestionItemIdentifier,
    public order?: number,
    public label?: string,
    public questionId?: number
  ) {}
}

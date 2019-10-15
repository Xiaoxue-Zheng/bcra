import { IQuestionSection } from 'app/shared/model/question-section.model';
import { IQuestion } from 'app/shared/model/question.model';

export const enum QuestionGroupIdentifier {
  CONSENT_FORM_QUESTIONS = 'CONSENT_FORM_QUESTIONS',
  FAMILY_BREAST_AFFECTED_QUESTIONS = 'FAMILY_BREAST_AFFECTED_QUESTIONS',
  FAMILY_BREAST_HOW_MANY_QUESTIONS = 'FAMILY_BREAST_HOW_MANY_QUESTIONS',
  FAMILY_BREAST_AGE_QUESTIONS = 'FAMILY_BREAST_AGE_QUESTIONS',
  FAMILY_OVARIAN_AFFECTED_QUESTIONS = 'FAMILY_OVARIAN_AFFECTED_QUESTIONS',
  FAMILY_OVARIAN_HOW_MANY_QUESTIONS = 'FAMILY_OVARIAN_HOW_MANY_QUESTIONS',
  FAMILY_OVARIAN_AGE_QUESTIONS = 'FAMILY_OVARIAN_AGE_QUESTIONS',
  FAMILY_AFFECTED_GRANDMOTHER_QUESTIONS = 'FAMILY_AFFECTED_GRANDMOTHER_QUESTIONS',
  FAMILY_AFFECTED_AUNT_QUESTIONS = 'FAMILY_AFFECTED_AUNT_QUESTIONS',
  FAMILY_AFFECTED_NIECE_QUESTIONS = 'FAMILY_AFFECTED_NIECE_QUESTIONS',
  FAMILY_AFFECTED_HALF_SISTER_QUESTIONS = 'FAMILY_AFFECTED_HALF_SISTER_QUESTIONS',
  PERSONAL_HISTORY_QUESTIONS = 'PERSONAL_HISTORY_QUESTIONS',
  MALE_BREAST_CANCER_QUESTIONS = 'MALE_BREAST_CANCER_QUESTIONS'
}

export interface IQuestionGroup {
  id?: number;
  identifier?: QuestionGroupIdentifier;
  questionSections?: IQuestionSection[];
  questions?: IQuestion[];
}

export class QuestionGroup implements IQuestionGroup {
  constructor(
    public id?: number,
    public identifier?: QuestionGroupIdentifier,
    public questionSections?: IQuestionSection[],
    public questions?: IQuestion[]
  ) {}
}

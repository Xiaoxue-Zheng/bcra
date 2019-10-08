import { IQuestionSection } from 'app/shared/model/question-section.model';
import { IQuestion } from 'app/shared/model/question.model';

export const enum QuestionGroupIdentifier {
  PERSONAL_HISTORY_QUESTIONS = 'PERSONAL_HISTORY_QUESTIONS',
  FAMILY_HISTORY_QUESTIONS = 'FAMILY_HISTORY_QUESTIONS',
  RELATIVE_QUESTIONS = 'RELATIVE_QUESTIONS',
  RELATIVE_COUNT_QUESTIONS = 'RELATIVE_COUNT_QUESTIONS',
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

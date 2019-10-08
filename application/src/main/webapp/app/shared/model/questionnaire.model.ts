import { IQuestionSection } from 'app/shared/model/question-section.model';

export const enum QuestionnaireIdentifier {
  TYRER_CUZICK_IV1 = 'TYRER_CUZICK_IV1'
}

export const enum Algorithm {
  TYRER_CUZICK = 'TYRER_CUZICK'
}

export interface IQuestionnaire {
  id?: number;
  identifier?: QuestionnaireIdentifier;
  algorithm?: Algorithm;
  algorithmMinimum?: number;
  algorithmMaximum?: number;
  implementationVersion?: number;
  questionSections?: IQuestionSection[];
}

export class Questionnaire implements IQuestionnaire {
  constructor(
    public id?: number,
    public identifier?: QuestionnaireIdentifier,
    public algorithm?: Algorithm,
    public algorithmMinimum?: number,
    public algorithmMaximum?: number,
    public implementationVersion?: number,
    public questionSections?: IQuestionSection[]
  ) {}
}

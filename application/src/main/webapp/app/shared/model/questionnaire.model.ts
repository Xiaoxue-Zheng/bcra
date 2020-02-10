import { IQuestionSection } from 'app/shared/model/question-section.model';

export const enum QuestionnaireType {
  CONSENT_FORM = 'CONSENT_FORM',
  RISK_ASSESSMENT = 'RISK_ASSESSMENT'
}

export interface IQuestionnaire {
  id?: number;
  type?: QuestionnaireType;
  version?: number;
  questionSections?: IQuestionSection[];
}

export class Questionnaire implements IQuestionnaire {
  constructor(public id?: number, public type?: QuestionnaireType, public version?: number, public questionSections?: IQuestionSection[]) {}
}

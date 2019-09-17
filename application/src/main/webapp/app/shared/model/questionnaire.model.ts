import { IQuestionnaireQuestionGroup } from 'app/shared/model/questionnaire-question-group.model';
import { IAnswerResponse } from 'app/shared/model/answer-response.model';

export interface IQuestionnaire {
  id?: number;
  uuid?: string;
  questionnaireQuestionGroups?: IQuestionnaireQuestionGroup[];
  answerResponses?: IAnswerResponse[];
}

export class Questionnaire implements IQuestionnaire {
  constructor(
    public id?: number,
    public uuid?: string,
    public questionnaireQuestionGroups?: IQuestionnaireQuestionGroup[],
    public answerResponses?: IAnswerResponse[]
  ) {}
}

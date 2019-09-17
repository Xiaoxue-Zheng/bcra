import { IAnswerGroup } from 'app/shared/model/answer-group.model';

export interface IAnswerResponse {
  id?: number;
  questionnaireId?: number;
  answerGroups?: IAnswerGroup[];
}

export class AnswerResponse implements IAnswerResponse {
  constructor(public id?: number, public questionnaireId?: number, public answerGroups?: IAnswerGroup[]) {}
}

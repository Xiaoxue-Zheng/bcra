import { IAnswer } from 'app/shared/model/answer.model';

export interface IAnswerGroup {
  id?: number;
  order?: number;
  answerSectionId?: number;
  answers?: IAnswer[];
}

export class AnswerGroup implements IAnswerGroup {
  constructor(public id?: number, public order?: number, public answerSectionId?: number, public answers?: IAnswer[]) {}
}

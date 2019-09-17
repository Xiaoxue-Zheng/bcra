import { IAnswer } from 'app/shared/model/answer.model';

export interface IAnswerGroup {
  id?: number;
  answerResponseId?: number;
  questionGroupId?: number;
  answers?: IAnswer[];
}

export class AnswerGroup implements IAnswerGroup {
  constructor(public id?: number, public answerResponseId?: number, public questionGroupId?: number, public answers?: IAnswer[]) {}
}

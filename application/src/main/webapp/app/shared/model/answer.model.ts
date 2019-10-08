import { IAnswerItem } from 'app/shared/model/answer-item.model';

export const enum AnswerUnits {
  GRAMS = 'GRAMS',
  POUNDS = 'POUNDS',
  CENTIMETERS = 'CENTIMETERS',
  INCHES = 'INCHES'
}

export interface IAnswer {
  id?: number;
  number?: number;
  units?: AnswerUnits;
  answerGroupId?: number;
  questionId?: number;
  answerItems?: IAnswerItem[];
}

export class Answer implements IAnswer {
  constructor(
    public id?: number,
    public number?: number,
    public units?: AnswerUnits,
    public answerGroupId?: number,
    public questionId?: number,
    public answerItems?: IAnswerItem[]
  ) {}
}

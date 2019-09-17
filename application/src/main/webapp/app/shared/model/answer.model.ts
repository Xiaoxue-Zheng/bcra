export interface IAnswer {
  id?: number;
  answerGroupId?: number;
  questionId?: number;
}

export class Answer implements IAnswer {
  constructor(public id?: number, public answerGroupId?: number, public questionId?: number) {}
}

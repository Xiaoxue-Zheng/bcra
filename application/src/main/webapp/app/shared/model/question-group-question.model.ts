export interface IQuestionGroupQuestion {
  id?: number;
  uuid?: string;
  questionGroupUuid?: string;
  questionUuid?: string;
  order?: number;
  questionGroupId?: number;
  questionId?: number;
}

export class QuestionGroupQuestion implements IQuestionGroupQuestion {
  constructor(
    public id?: number,
    public uuid?: string,
    public questionGroupUuid?: string,
    public questionUuid?: string,
    public order?: number,
    public questionGroupId?: number,
    public questionId?: number
  ) {}
}

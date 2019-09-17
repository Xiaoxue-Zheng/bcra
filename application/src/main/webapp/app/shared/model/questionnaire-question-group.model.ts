export interface IQuestionnaireQuestionGroup {
  id?: number;
  uuid?: string;
  order?: number;
  questionnaireId?: number;
  questionGroupId?: number;
}

export class QuestionnaireQuestionGroup implements IQuestionnaireQuestionGroup {
  constructor(
    public id?: number,
    public uuid?: string,
    public order?: number,
    public questionnaireId?: number,
    public questionGroupId?: number
  ) {}
}

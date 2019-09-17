export interface IQuestionnaireQuestionGroup {
  id?: number;
  uuid?: string;
  questionnaireUuid?: string;
  questionGroupUuid?: string;
  order?: number;
  questionnaireId?: number;
  questionGroupId?: number;
}

export class QuestionnaireQuestionGroup implements IQuestionnaireQuestionGroup {
  constructor(
    public id?: number,
    public uuid?: string,
    public questionnaireUuid?: string,
    public questionGroupUuid?: string,
    public order?: number,
    public questionnaireId?: number,
    public questionGroupId?: number
  ) {}
}

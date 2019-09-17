import { IDisplayCondition } from 'app/shared/model/display-condition.model';
import { IQuestionnaireQuestionGroup } from 'app/shared/model/questionnaire-question-group.model';
import { IQuestionGroupQuestion } from 'app/shared/model/question-group-question.model';
import { IAnswerGroup } from 'app/shared/model/answer-group.model';

export interface IQuestionGroup {
  id?: number;
  uuid?: string;
  displayConditions?: IDisplayCondition[];
  questionnaireQuestionGroups?: IQuestionnaireQuestionGroup[];
  questionGroupQuestions?: IQuestionGroupQuestion[];
  answerGroups?: IAnswerGroup[];
}

export class QuestionGroup implements IQuestionGroup {
  constructor(
    public id?: number,
    public uuid?: string,
    public displayConditions?: IDisplayCondition[],
    public questionnaireQuestionGroups?: IQuestionnaireQuestionGroup[],
    public questionGroupQuestions?: IQuestionGroupQuestion[],
    public answerGroups?: IAnswerGroup[]
  ) {}
}

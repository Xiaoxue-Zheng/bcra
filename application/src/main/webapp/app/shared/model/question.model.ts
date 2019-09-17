import { IQuestionGroupQuestion } from 'app/shared/model/question-group-question.model';
import { IAnswer } from 'app/shared/model/answer.model';

export interface IQuestion {
  id?: number;
  uuid?: string;
  text?: string;
  questionGroupQuestions?: IQuestionGroupQuestion[];
  answers?: IAnswer[];
}

export class Question implements IQuestion {
  constructor(
    public id?: number,
    public uuid?: string,
    public text?: string,
    public questionGroupQuestions?: IQuestionGroupQuestion[],
    public answers?: IAnswer[]
  ) {}
}

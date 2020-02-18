import { IQuestionSection } from 'app/shared/model/question-section.model';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionGroupIdentifier } from 'app/shared/model/enumerations/question-group-identifier.model';

export interface IQuestionGroup {
  id?: number;
  identifier?: QuestionGroupIdentifier;
  questionSections?: IQuestionSection[];
  questions?: IQuestion[];
}

export class QuestionGroup implements IQuestionGroup {
  constructor(
    public id?: number,
    public identifier?: QuestionGroupIdentifier,
    public questionSections?: IQuestionSection[],
    public questions?: IQuestion[]
  ) {}
}

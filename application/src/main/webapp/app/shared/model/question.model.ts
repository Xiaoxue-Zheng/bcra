import { IDisplayCondition } from 'app/shared/model/display-condition.model';
import { IQuestionItem } from 'app/shared/model/question-item.model';
import { QuestionIdentifier } from 'app/shared/model/enumerations/question-identifier.model';
import { QuestionType } from 'app/shared/model/enumerations/question-type.model';

export interface IQuestion {
  id?: number;
  identifier?: QuestionIdentifier;
  type?: QuestionType;
  order?: number;
  text?: string;
  variableName?: string;
  minimum?: number;
  maximum?: number;
  hint?: string;
  hintText?: string;
  displayConditions?: IDisplayCondition[];
  questionItems?: IQuestionItem[];
  questionGroupId?: number;
}

export class Question implements IQuestion {
  constructor(
    public id?: number,
    public identifier?: QuestionIdentifier,
    public type?: QuestionType,
    public order?: number,
    public text?: string,
    public variableName?: string,
    public minimum?: number,
    public maximum?: number,
    public hint?: string,
    public hintText?: string,
    public displayConditions?: IDisplayCondition[],
    public questionItems?: IQuestionItem[],
    public questionGroupId?: number
  ) {}
}

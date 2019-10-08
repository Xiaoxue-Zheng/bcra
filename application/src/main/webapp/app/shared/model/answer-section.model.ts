import { IAnswerGroup } from 'app/shared/model/answer-group.model';

export interface IAnswerSection {
  id?: number;
  answerResponseId?: number;
  questionSectionId?: number;
  answerGroups?: IAnswerGroup[];
}

export class AnswerSection implements IAnswerSection {
  constructor(
    public id?: number,
    public answerResponseId?: number,
    public questionSectionId?: number,
    public answerGroups?: IAnswerGroup[]
  ) {}
}

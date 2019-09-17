import { ICheckboxAnswerItem } from 'app/shared/model/checkbox-answer-item.model';
import { ICheckboxDisplayCondition } from 'app/shared/model/checkbox-display-condition.model';

export interface ICheckboxQuestionItem {
  id?: number;
  uuid?: string;
  questionUuid?: string;
  label?: string;
  descriptor?: string;
  checkboxQuestionId?: number;
  checkboxAnswerItems?: ICheckboxAnswerItem[];
  checkboxDisplayConditions?: ICheckboxDisplayCondition[];
}

export class CheckboxQuestionItem implements ICheckboxQuestionItem {
  constructor(
    public id?: number,
    public uuid?: string,
    public questionUuid?: string,
    public label?: string,
    public descriptor?: string,
    public checkboxQuestionId?: number,
    public checkboxAnswerItems?: ICheckboxAnswerItem[],
    public checkboxDisplayConditions?: ICheckboxDisplayCondition[]
  ) {}
}

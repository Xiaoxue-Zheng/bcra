import { ICheckboxAnswerItem } from 'app/shared/model/checkbox-answer-item.model';

export interface ICheckboxAnswer {
  id?: number;
  checkboxAnswerItems?: ICheckboxAnswerItem[];
}

export class CheckboxAnswer implements ICheckboxAnswer {
  constructor(public id?: number, public checkboxAnswerItems?: ICheckboxAnswerItem[]) {}
}

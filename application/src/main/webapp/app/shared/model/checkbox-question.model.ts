import { ICheckboxQuestionItem } from 'app/shared/model/checkbox-question-item.model';

export interface ICheckboxQuestion {
  id?: number;
  checkboxQuestionItems?: ICheckboxQuestionItem[];
}

export class CheckboxQuestion implements ICheckboxQuestion {
  constructor(public id?: number, public checkboxQuestionItems?: ICheckboxQuestionItem[]) {}
}

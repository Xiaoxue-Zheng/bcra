export interface ICheckboxAnswerItem {
  id?: number;
  checkboxAnswerId?: number;
  checkboxQuestionItemId?: number;
}

export class CheckboxAnswerItem implements ICheckboxAnswerItem {
  constructor(public id?: number, public checkboxAnswerId?: number, public checkboxQuestionItemId?: number) {}
}

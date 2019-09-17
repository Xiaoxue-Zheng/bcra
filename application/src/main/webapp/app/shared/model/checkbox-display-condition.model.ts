export interface ICheckboxDisplayCondition {
  id?: number;
  checkboxQuestionItemId?: number;
}

export class CheckboxDisplayCondition implements ICheckboxDisplayCondition {
  constructor(public id?: number, public checkboxQuestionItemId?: number) {}
}

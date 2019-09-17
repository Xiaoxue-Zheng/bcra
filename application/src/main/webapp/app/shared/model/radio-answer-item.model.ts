export interface IRadioAnswerItem {
  id?: number;
  radioAnswerId?: number;
}

export class RadioAnswerItem implements IRadioAnswerItem {
  constructor(public id?: number, public radioAnswerId?: number) {}
}

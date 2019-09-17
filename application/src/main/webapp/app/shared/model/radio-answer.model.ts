export interface IRadioAnswer {
  id?: number;
  radioAnswerItemId?: number;
}

export class RadioAnswer implements IRadioAnswer {
  constructor(public id?: number, public radioAnswerItemId?: number) {}
}

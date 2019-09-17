export interface INumberCheckboxQuestion {
  id?: number;
  minimum?: number;
  maximum?: number;
}

export class NumberCheckboxQuestion implements INumberCheckboxQuestion {
  constructor(public id?: number, public minimum?: number, public maximum?: number) {}
}

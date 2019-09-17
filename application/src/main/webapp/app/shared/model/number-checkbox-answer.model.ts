export interface INumberCheckboxAnswer {
  id?: number;
  number?: number;
  check?: boolean;
}

export class NumberCheckboxAnswer implements INumberCheckboxAnswer {
  constructor(public id?: number, public number?: number, public check?: boolean) {
    this.check = this.check || false;
  }
}

export interface IRepeatAnswer {
  id?: number;
  quantity?: number;
}

export class RepeatAnswer implements IRepeatAnswer {
  constructor(public id?: number, public quantity?: number) {}
}

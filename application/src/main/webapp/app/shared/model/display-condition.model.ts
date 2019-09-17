export interface IDisplayCondition {
  id?: number;
  questionGroupId?: number;
}

export class DisplayCondition implements IDisplayCondition {
  constructor(public id?: number, public questionGroupId?: number) {}
}

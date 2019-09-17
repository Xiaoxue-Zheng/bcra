export interface IRepeatDisplayCondition {
  id?: number;
  repeatQuestionId?: number;
}

export class RepeatDisplayCondition implements IRepeatDisplayCondition {
  constructor(public id?: number, public repeatQuestionId?: number) {}
}

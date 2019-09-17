import { IRepeatDisplayCondition } from 'app/shared/model/repeat-display-condition.model';

export interface IRepeatQuestion {
  id?: number;
  maximum?: number;
  repeatDisplayConditions?: IRepeatDisplayCondition[];
}

export class RepeatQuestion implements IRepeatQuestion {
  constructor(public id?: number, public maximum?: number, public repeatDisplayConditions?: IRepeatDisplayCondition[]) {}
}

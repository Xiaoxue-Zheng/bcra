export interface IAnswerItem {
  id?: number;
  selected?: boolean;
  answerId?: number;
  questionItemId?: number;
}

export class AnswerItem implements IAnswerItem {
  constructor(public id?: number, public selected?: boolean, public answerId?: number, public questionItemId?: number) {
    this.selected = this.selected || false;
  }
}

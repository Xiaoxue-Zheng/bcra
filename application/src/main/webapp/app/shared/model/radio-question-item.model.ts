export interface IRadioQuestionItem {
  id?: number;
  uuid?: string;
  questionUuid?: string;
  label?: string;
  descriptor?: string;
  radioQuestionId?: number;
}

export class RadioQuestionItem implements IRadioQuestionItem {
  constructor(
    public id?: number,
    public uuid?: string,
    public questionUuid?: string,
    public label?: string,
    public descriptor?: string,
    public radioQuestionId?: number
  ) {}
}

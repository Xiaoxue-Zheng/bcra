import { IRadioQuestionItem } from 'app/shared/model/radio-question-item.model';

export interface IRadioQuestion {
  id?: number;
  radioQuestionItems?: IRadioQuestionItem[];
}

export class RadioQuestion implements IRadioQuestion {
  constructor(public id?: number, public radioQuestionItems?: IRadioQuestionItem[]) {}
}

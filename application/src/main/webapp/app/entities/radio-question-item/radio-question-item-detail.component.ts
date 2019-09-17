import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRadioQuestionItem } from 'app/shared/model/radio-question-item.model';

@Component({
  selector: 'jhi-radio-question-item-detail',
  templateUrl: './radio-question-item-detail.component.html'
})
export class RadioQuestionItemDetailComponent implements OnInit {
  radioQuestionItem: IRadioQuestionItem;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ radioQuestionItem }) => {
      this.radioQuestionItem = radioQuestionItem;
    });
  }

  previousState() {
    window.history.back();
  }
}

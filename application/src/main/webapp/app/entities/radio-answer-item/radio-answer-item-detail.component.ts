import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRadioAnswerItem } from 'app/shared/model/radio-answer-item.model';

@Component({
  selector: 'jhi-radio-answer-item-detail',
  templateUrl: './radio-answer-item-detail.component.html'
})
export class RadioAnswerItemDetailComponent implements OnInit {
  radioAnswerItem: IRadioAnswerItem;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ radioAnswerItem }) => {
      this.radioAnswerItem = radioAnswerItem;
    });
  }

  previousState() {
    window.history.back();
  }
}

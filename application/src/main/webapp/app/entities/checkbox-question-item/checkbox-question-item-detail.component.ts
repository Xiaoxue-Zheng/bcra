import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICheckboxQuestionItem } from 'app/shared/model/checkbox-question-item.model';

@Component({
  selector: 'jhi-checkbox-question-item-detail',
  templateUrl: './checkbox-question-item-detail.component.html'
})
export class CheckboxQuestionItemDetailComponent implements OnInit {
  checkboxQuestionItem: ICheckboxQuestionItem;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ checkboxQuestionItem }) => {
      this.checkboxQuestionItem = checkboxQuestionItem;
    });
  }

  previousState() {
    window.history.back();
  }
}

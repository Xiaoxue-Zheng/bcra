import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICheckboxAnswerItem } from 'app/shared/model/checkbox-answer-item.model';

@Component({
  selector: 'jhi-checkbox-answer-item-detail',
  templateUrl: './checkbox-answer-item-detail.component.html'
})
export class CheckboxAnswerItemDetailComponent implements OnInit {
  checkboxAnswerItem: ICheckboxAnswerItem;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ checkboxAnswerItem }) => {
      this.checkboxAnswerItem = checkboxAnswerItem;
    });
  }

  previousState() {
    window.history.back();
  }
}

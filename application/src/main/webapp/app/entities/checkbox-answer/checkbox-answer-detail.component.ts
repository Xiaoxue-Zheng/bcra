import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICheckboxAnswer } from 'app/shared/model/checkbox-answer.model';

@Component({
  selector: 'jhi-checkbox-answer-detail',
  templateUrl: './checkbox-answer-detail.component.html'
})
export class CheckboxAnswerDetailComponent implements OnInit {
  checkboxAnswer: ICheckboxAnswer;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ checkboxAnswer }) => {
      this.checkboxAnswer = checkboxAnswer;
    });
  }

  previousState() {
    window.history.back();
  }
}

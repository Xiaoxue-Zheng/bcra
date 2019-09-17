import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INumberCheckboxAnswer } from 'app/shared/model/number-checkbox-answer.model';

@Component({
  selector: 'jhi-number-checkbox-answer-detail',
  templateUrl: './number-checkbox-answer-detail.component.html'
})
export class NumberCheckboxAnswerDetailComponent implements OnInit {
  numberCheckboxAnswer: INumberCheckboxAnswer;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ numberCheckboxAnswer }) => {
      this.numberCheckboxAnswer = numberCheckboxAnswer;
    });
  }

  previousState() {
    window.history.back();
  }
}

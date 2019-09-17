import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INumberCheckboxQuestion } from 'app/shared/model/number-checkbox-question.model';

@Component({
  selector: 'jhi-number-checkbox-question-detail',
  templateUrl: './number-checkbox-question-detail.component.html'
})
export class NumberCheckboxQuestionDetailComponent implements OnInit {
  numberCheckboxQuestion: INumberCheckboxQuestion;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ numberCheckboxQuestion }) => {
      this.numberCheckboxQuestion = numberCheckboxQuestion;
    });
  }

  previousState() {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICheckboxQuestion } from 'app/shared/model/checkbox-question.model';

@Component({
  selector: 'jhi-checkbox-question-detail',
  templateUrl: './checkbox-question-detail.component.html'
})
export class CheckboxQuestionDetailComponent implements OnInit {
  checkboxQuestion: ICheckboxQuestion;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ checkboxQuestion }) => {
      this.checkboxQuestion = checkboxQuestion;
    });
  }

  previousState() {
    window.history.back();
  }
}

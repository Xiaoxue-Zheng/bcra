import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRadioQuestion } from 'app/shared/model/radio-question.model';

@Component({
  selector: 'jhi-radio-question-detail',
  templateUrl: './radio-question-detail.component.html'
})
export class RadioQuestionDetailComponent implements OnInit {
  radioQuestion: IRadioQuestion;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ radioQuestion }) => {
      this.radioQuestion = radioQuestion;
    });
  }

  previousState() {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRadioAnswer } from 'app/shared/model/radio-answer.model';

@Component({
  selector: 'jhi-radio-answer-detail',
  templateUrl: './radio-answer-detail.component.html'
})
export class RadioAnswerDetailComponent implements OnInit {
  radioAnswer: IRadioAnswer;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ radioAnswer }) => {
      this.radioAnswer = radioAnswer;
    });
  }

  previousState() {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRepeatAnswer } from 'app/shared/model/repeat-answer.model';

@Component({
  selector: 'jhi-repeat-answer-detail',
  templateUrl: './repeat-answer-detail.component.html'
})
export class RepeatAnswerDetailComponent implements OnInit {
  repeatAnswer: IRepeatAnswer;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ repeatAnswer }) => {
      this.repeatAnswer = repeatAnswer;
    });
  }

  previousState() {
    window.history.back();
  }
}

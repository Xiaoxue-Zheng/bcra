import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRepeatQuestion } from 'app/shared/model/repeat-question.model';

@Component({
  selector: 'jhi-repeat-question-detail',
  templateUrl: './repeat-question-detail.component.html'
})
export class RepeatQuestionDetailComponent implements OnInit {
  repeatQuestion: IRepeatQuestion;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ repeatQuestion }) => {
      this.repeatQuestion = repeatQuestion;
    });
  }

  previousState() {
    window.history.back();
  }
}

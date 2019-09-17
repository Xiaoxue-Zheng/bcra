import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnswerResponse } from 'app/shared/model/answer-response.model';

@Component({
  selector: 'jhi-answer-response-detail',
  templateUrl: './answer-response-detail.component.html'
})
export class AnswerResponseDetailComponent implements OnInit {
  answerResponse: IAnswerResponse;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ answerResponse }) => {
      this.answerResponse = answerResponse;
    });
  }

  previousState() {
    window.history.back();
  }
}

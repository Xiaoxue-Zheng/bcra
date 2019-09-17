import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnswerGroup } from 'app/shared/model/answer-group.model';

@Component({
  selector: 'jhi-answer-group-detail',
  templateUrl: './answer-group-detail.component.html'
})
export class AnswerGroupDetailComponent implements OnInit {
  answerGroup: IAnswerGroup;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ answerGroup }) => {
      this.answerGroup = answerGroup;
    });
  }

  previousState() {
    window.history.back();
  }
}

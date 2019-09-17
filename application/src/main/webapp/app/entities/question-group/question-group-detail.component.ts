import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestionGroup } from 'app/shared/model/question-group.model';

@Component({
  selector: 'jhi-question-group-detail',
  templateUrl: './question-group-detail.component.html'
})
export class QuestionGroupDetailComponent implements OnInit {
  questionGroup: IQuestionGroup;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ questionGroup }) => {
      this.questionGroup = questionGroup;
    });
  }

  previousState() {
    window.history.back();
  }
}

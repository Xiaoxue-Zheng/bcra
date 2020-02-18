import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestionGroup } from 'app/shared/model/question-group.model';

@Component({
  selector: 'jhi-question-group-detail',
  templateUrl: './question-group-detail.component.html'
})
export class QuestionGroupDetailComponent implements OnInit {
  questionGroup: IQuestionGroup | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionGroup }) => (this.questionGroup = questionGroup));
  }

  previousState(): void {
    window.history.back();
  }
}

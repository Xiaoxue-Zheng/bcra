import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestionGroupQuestion } from 'app/shared/model/question-group-question.model';

@Component({
  selector: 'jhi-question-group-question-detail',
  templateUrl: './question-group-question-detail.component.html'
})
export class QuestionGroupQuestionDetailComponent implements OnInit {
  questionGroupQuestion: IQuestionGroupQuestion;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ questionGroupQuestion }) => {
      this.questionGroupQuestion = questionGroupQuestion;
    });
  }

  previousState() {
    window.history.back();
  }
}

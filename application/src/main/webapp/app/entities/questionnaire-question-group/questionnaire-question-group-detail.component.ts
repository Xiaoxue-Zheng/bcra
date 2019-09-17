import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestionnaireQuestionGroup } from 'app/shared/model/questionnaire-question-group.model';

@Component({
  selector: 'jhi-questionnaire-question-group-detail',
  templateUrl: './questionnaire-question-group-detail.component.html'
})
export class QuestionnaireQuestionGroupDetailComponent implements OnInit {
  questionnaireQuestionGroup: IQuestionnaireQuestionGroup;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ questionnaireQuestionGroup }) => {
      this.questionnaireQuestionGroup = questionnaireQuestionGroup;
    });
  }

  previousState() {
    window.history.back();
  }
}

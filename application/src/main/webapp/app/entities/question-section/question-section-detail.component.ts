import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestionSection } from 'app/shared/model/question-section.model';

@Component({
  selector: 'jhi-question-section-detail',
  templateUrl: './question-section-detail.component.html'
})
export class QuestionSectionDetailComponent implements OnInit {
  questionSection: IQuestionSection;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ questionSection }) => {
      this.questionSection = questionSection;
    });
  }

  previousState() {
    window.history.back();
  }
}

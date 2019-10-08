import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnswerSection } from 'app/shared/model/answer-section.model';

@Component({
  selector: 'jhi-answer-section-detail',
  templateUrl: './answer-section-detail.component.html'
})
export class AnswerSectionDetailComponent implements OnInit {
  answerSection: IAnswerSection;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ answerSection }) => {
      this.answerSection = answerSection;
    });
  }

  previousState() {
    window.history.back();
  }
}

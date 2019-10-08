import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnswerItem } from 'app/shared/model/answer-item.model';

@Component({
  selector: 'jhi-answer-item-detail',
  templateUrl: './answer-item-detail.component.html'
})
export class AnswerItemDetailComponent implements OnInit {
  answerItem: IAnswerItem;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ answerItem }) => {
      this.answerItem = answerItem;
    });
  }

  previousState() {
    window.history.back();
  }
}

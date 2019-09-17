import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRepeatDisplayCondition } from 'app/shared/model/repeat-display-condition.model';

@Component({
  selector: 'jhi-repeat-display-condition-detail',
  templateUrl: './repeat-display-condition-detail.component.html'
})
export class RepeatDisplayConditionDetailComponent implements OnInit {
  repeatDisplayCondition: IRepeatDisplayCondition;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ repeatDisplayCondition }) => {
      this.repeatDisplayCondition = repeatDisplayCondition;
    });
  }

  previousState() {
    window.history.back();
  }
}

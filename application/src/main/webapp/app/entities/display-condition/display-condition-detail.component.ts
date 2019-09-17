import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDisplayCondition } from 'app/shared/model/display-condition.model';

@Component({
  selector: 'jhi-display-condition-detail',
  templateUrl: './display-condition-detail.component.html'
})
export class DisplayConditionDetailComponent implements OnInit {
  displayCondition: IDisplayCondition;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ displayCondition }) => {
      this.displayCondition = displayCondition;
    });
  }

  previousState() {
    window.history.back();
  }
}

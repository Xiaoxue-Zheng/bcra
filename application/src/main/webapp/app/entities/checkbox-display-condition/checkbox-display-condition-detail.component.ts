import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICheckboxDisplayCondition } from 'app/shared/model/checkbox-display-condition.model';

@Component({
  selector: 'jhi-checkbox-display-condition-detail',
  templateUrl: './checkbox-display-condition-detail.component.html'
})
export class CheckboxDisplayConditionDetailComponent implements OnInit {
  checkboxDisplayCondition: ICheckboxDisplayCondition;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ checkboxDisplayCondition }) => {
      this.checkboxDisplayCondition = checkboxDisplayCondition;
    });
  }

  previousState() {
    window.history.back();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReferralCondition } from 'app/shared/model/referral-condition.model';

@Component({
  selector: 'jhi-referral-condition-detail',
  templateUrl: './referral-condition-detail.component.html'
})
export class ReferralConditionDetailComponent implements OnInit {
  referralCondition: IReferralCondition | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ referralCondition }) => (this.referralCondition = referralCondition));
  }

  previousState(): void {
    window.history.back();
  }
}

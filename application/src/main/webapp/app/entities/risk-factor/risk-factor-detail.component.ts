import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRiskFactor } from 'app/shared/model/risk-factor.model';

@Component({
  selector: 'jhi-risk-factor-detail',
  templateUrl: './risk-factor-detail.component.html'
})
export class RiskFactorDetailComponent implements OnInit {
  riskFactor: IRiskFactor | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ riskFactor }) => (this.riskFactor = riskFactor));
  }

  previousState(): void {
    window.history.back();
  }
}

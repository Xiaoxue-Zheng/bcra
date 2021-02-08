import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRiskAssessmentResult } from 'app/shared/model/risk-assessment-result.model';

@Component({
  selector: 'jhi-risk-assessment-result-detail',
  templateUrl: './risk-assessment-result-detail.component.html'
})
export class RiskAssessmentResultDetailComponent implements OnInit {
  riskAssessmentResult: IRiskAssessmentResult | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ riskAssessmentResult }) => (this.riskAssessmentResult = riskAssessmentResult));
  }

  previousState(): void {
    window.history.back();
  }
}

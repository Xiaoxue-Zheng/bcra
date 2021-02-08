import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRiskAssessmentResult } from 'app/shared/model/risk-assessment-result.model';
import { RiskAssessmentResultService } from './risk-assessment-result.service';

@Component({
  templateUrl: './risk-assessment-result-delete-dialog.component.html'
})
export class RiskAssessmentResultDeleteDialogComponent {
  riskAssessmentResult?: IRiskAssessmentResult;

  constructor(
    protected riskAssessmentResultService: RiskAssessmentResultService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.riskAssessmentResultService.delete(id).subscribe(() => {
      this.eventManager.broadcast('riskAssessmentResultListModification');
      this.activeModal.close();
    });
  }
}

import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRiskFactor } from 'app/shared/model/risk-factor.model';
import { RiskFactorService } from './risk-factor.service';

@Component({
  templateUrl: './risk-factor-delete-dialog.component.html'
})
export class RiskFactorDeleteDialogComponent {
  riskFactor?: IRiskFactor;

  constructor(
    protected riskFactorService: RiskFactorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.riskFactorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('riskFactorListModification');
      this.activeModal.close();
    });
  }
}

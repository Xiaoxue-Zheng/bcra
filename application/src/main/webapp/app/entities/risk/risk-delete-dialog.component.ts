import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRisk } from 'app/shared/model/risk.model';
import { RiskService } from './risk.service';

@Component({
  templateUrl: './risk-delete-dialog.component.html'
})
export class RiskDeleteDialogComponent {
  risk?: IRisk;

  constructor(protected riskService: RiskService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.riskService.delete(id).subscribe(() => {
      this.eventManager.broadcast('riskListModification');
      this.activeModal.close();
    });
  }
}

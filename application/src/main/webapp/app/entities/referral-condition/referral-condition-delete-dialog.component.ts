import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReferralCondition } from 'app/shared/model/referral-condition.model';
import { ReferralConditionService } from './referral-condition.service';

@Component({
  templateUrl: './referral-condition-delete-dialog.component.html'
})
export class ReferralConditionDeleteDialogComponent {
  referralCondition?: IReferralCondition;

  constructor(
    protected referralConditionService: ReferralConditionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.referralConditionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('referralConditionListModification');
      this.activeModal.close();
    });
  }
}

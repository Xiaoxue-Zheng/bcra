import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReferralCondition } from 'app/shared/model/referral-condition.model';
import { ReferralConditionService } from './referral-condition.service';

@Component({
  selector: 'jhi-referral-condition-delete-dialog',
  templateUrl: './referral-condition-delete-dialog.component.html'
})
export class ReferralConditionDeleteDialogComponent {
  referralCondition: IReferralCondition;

  constructor(
    protected referralConditionService: ReferralConditionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.referralConditionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'referralConditionListModification',
        content: 'Deleted an referralCondition'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-referral-condition-delete-popup',
  template: ''
})
export class ReferralConditionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ referralCondition }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ReferralConditionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.referralCondition = referralCondition;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/referral-condition', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/referral-condition', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}

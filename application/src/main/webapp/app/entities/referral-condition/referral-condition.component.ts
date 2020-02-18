import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReferralCondition } from 'app/shared/model/referral-condition.model';
import { ReferralConditionService } from './referral-condition.service';
import { ReferralConditionDeleteDialogComponent } from './referral-condition-delete-dialog.component';

@Component({
  selector: 'jhi-referral-condition',
  templateUrl: './referral-condition.component.html'
})
export class ReferralConditionComponent implements OnInit, OnDestroy {
  referralConditions?: IReferralCondition[];
  eventSubscriber?: Subscription;

  constructor(
    protected referralConditionService: ReferralConditionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.referralConditionService
      .query()
      .subscribe((res: HttpResponse<IReferralCondition[]>) => (this.referralConditions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInReferralConditions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IReferralCondition): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInReferralConditions(): void {
    this.eventSubscriber = this.eventManager.subscribe('referralConditionListModification', () => this.loadAll());
  }

  delete(referralCondition: IReferralCondition): void {
    const modalRef = this.modalService.open(ReferralConditionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.referralCondition = referralCondition;
  }
}

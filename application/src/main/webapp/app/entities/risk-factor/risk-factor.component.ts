import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRiskFactor } from 'app/shared/model/risk-factor.model';
import { RiskFactorService } from './risk-factor.service';
import { RiskFactorDeleteDialogComponent } from './risk-factor-delete-dialog.component';

@Component({
  selector: 'jhi-risk-factor',
  templateUrl: './risk-factor.component.html'
})
export class RiskFactorComponent implements OnInit, OnDestroy {
  riskFactors?: IRiskFactor[];
  eventSubscriber?: Subscription;

  constructor(protected riskFactorService: RiskFactorService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.riskFactorService.query().subscribe((res: HttpResponse<IRiskFactor[]>) => (this.riskFactors = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRiskFactors();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRiskFactor): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRiskFactors(): void {
    this.eventSubscriber = this.eventManager.subscribe('riskFactorListModification', () => this.loadAll());
  }

  delete(riskFactor: IRiskFactor): void {
    const modalRef = this.modalService.open(RiskFactorDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.riskFactor = riskFactor;
  }
}

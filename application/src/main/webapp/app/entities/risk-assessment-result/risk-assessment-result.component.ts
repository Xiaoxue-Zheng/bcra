import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRiskAssessmentResult } from 'app/shared/model/risk-assessment-result.model';
import { RiskAssessmentResultService } from './risk-assessment-result.service';
import { RiskAssessmentResultDeleteDialogComponent } from './risk-assessment-result-delete-dialog.component';

@Component({
  selector: 'jhi-risk-assessment-result',
  templateUrl: './risk-assessment-result.component.html'
})
export class RiskAssessmentResultComponent implements OnInit, OnDestroy {
  riskAssessmentResults?: IRiskAssessmentResult[];
  eventSubscriber?: Subscription;

  constructor(
    protected riskAssessmentResultService: RiskAssessmentResultService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.riskAssessmentResultService
      .query()
      .subscribe((res: HttpResponse<IRiskAssessmentResult[]>) => (this.riskAssessmentResults = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRiskAssessmentResults();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRiskAssessmentResult): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRiskAssessmentResults(): void {
    this.eventSubscriber = this.eventManager.subscribe('riskAssessmentResultListModification', () => this.loadAll());
  }

  delete(riskAssessmentResult: IRiskAssessmentResult): void {
    const modalRef = this.modalService.open(RiskAssessmentResultDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.riskAssessmentResult = riskAssessmentResult;
  }
}

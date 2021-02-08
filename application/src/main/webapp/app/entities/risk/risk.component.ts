import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRisk } from 'app/shared/model/risk.model';
import { RiskService } from './risk.service';
import { RiskDeleteDialogComponent } from './risk-delete-dialog.component';

@Component({
  selector: 'jhi-risk',
  templateUrl: './risk.component.html'
})
export class RiskComponent implements OnInit, OnDestroy {
  risks?: IRisk[];
  eventSubscriber?: Subscription;

  constructor(protected riskService: RiskService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.riskService.query().subscribe((res: HttpResponse<IRisk[]>) => (this.risks = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRisks();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRisk): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRisks(): void {
    this.eventSubscriber = this.eventManager.subscribe('riskListModification', () => this.loadAll());
  }

  delete(risk: IRisk): void {
    const modalRef = this.modalService.open(RiskDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.risk = risk;
  }
}

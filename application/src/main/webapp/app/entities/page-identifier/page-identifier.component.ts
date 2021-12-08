import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IPageIdentifier } from 'app/shared/model/page-identifier.model';
import { PageIdentifierService } from './page-identifier.service';

@Component({
  selector: 'jhi-page-identifier',
  templateUrl: './page-identifier.component.html'
})
export class PageIdentifierComponent implements OnInit, OnDestroy {
  pageIdentifiers?: IPageIdentifier[];
  eventSubscriber?: Subscription;

  constructor(protected pageIdentifierService: PageIdentifierService, protected eventManager: JhiEventManager) {}

  loadAll(): void {
    this.pageIdentifierService.query().subscribe((res: HttpResponse<IPageIdentifier[]>) => (this.pageIdentifiers = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPageIdentifiers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPageIdentifier): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPageIdentifiers(): void {
    this.eventSubscriber = this.eventManager.subscribe('pageIdentifierListModification', () => this.loadAll());
  }
}

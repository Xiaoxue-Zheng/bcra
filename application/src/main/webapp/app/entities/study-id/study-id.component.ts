import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStudyId } from 'app/shared/model/study-id.model';
import { StudyIdService } from './study-id.service';
import { StudyIdDeleteDialogComponent } from './study-id-delete-dialog.component';

@Component({
  selector: 'jhi-study-id',
  templateUrl: './study-id.component.html'
})
export class StudyIdComponent implements OnInit, OnDestroy {
  studyIds?: IStudyId[];
  eventSubscriber?: Subscription;

  constructor(protected studyIdService: StudyIdService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.studyIdService.query().subscribe((res: HttpResponse<IStudyId[]>) => (this.studyIds = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInStudyIds();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IStudyId): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInStudyIds(): void {
    this.eventSubscriber = this.eventManager.subscribe('studyIdListModification', () => this.loadAll());
  }

  delete(studyId: IStudyId): void {
    const modalRef = this.modalService.open(StudyIdDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.studyId = studyId;
  }
}

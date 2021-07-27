import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStudyId } from 'app/shared/model/study-id.model';
import { StudyIdService } from './study-id.service';
import { StudyIdDeleteDialogComponent } from './study-id-delete-dialog.component';
import { filter, map } from 'rxjs/operators';

@Component({
  selector: 'jhi-study-id',
  templateUrl: './study-id.component.html'
})
export class StudyIdComponent implements OnInit, OnDestroy {
  studyIds?: IStudyId[];
  eventSubscriber?: Subscription;

  constructor(protected studyIdService: StudyIdService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.studyIdService
      .query()
      .pipe(
        filter((res: HttpResponse<IStudyId[]>) => res.ok),
        map((res: HttpResponse<IStudyId[]>) => res.body)
      )
      .subscribe(
        (res: IStudyId[]) => {
          this.studyIds = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
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

  onError(message) {
    console.log(message);
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

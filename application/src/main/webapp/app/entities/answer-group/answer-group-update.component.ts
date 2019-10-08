import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAnswerGroup, AnswerGroup } from 'app/shared/model/answer-group.model';
import { AnswerGroupService } from './answer-group.service';
import { IAnswerSection } from 'app/shared/model/answer-section.model';
import { AnswerSectionService } from 'app/entities/answer-section';

@Component({
  selector: 'jhi-answer-group-update',
  templateUrl: './answer-group-update.component.html'
})
export class AnswerGroupUpdateComponent implements OnInit {
  isSaving: boolean;

  answersections: IAnswerSection[];

  editForm = this.fb.group({
    id: [],
    order: [null, [Validators.required]],
    answerSectionId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected answerGroupService: AnswerGroupService,
    protected answerSectionService: AnswerSectionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ answerGroup }) => {
      this.updateForm(answerGroup);
    });
    this.answerSectionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAnswerSection[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAnswerSection[]>) => response.body)
      )
      .subscribe((res: IAnswerSection[]) => (this.answersections = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(answerGroup: IAnswerGroup) {
    this.editForm.patchValue({
      id: answerGroup.id,
      order: answerGroup.order,
      answerSectionId: answerGroup.answerSectionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const answerGroup = this.createFromForm();
    if (answerGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.answerGroupService.update(answerGroup));
    } else {
      this.subscribeToSaveResponse(this.answerGroupService.create(answerGroup));
    }
  }

  private createFromForm(): IAnswerGroup {
    return {
      ...new AnswerGroup(),
      id: this.editForm.get(['id']).value,
      order: this.editForm.get(['order']).value,
      answerSectionId: this.editForm.get(['answerSectionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnswerGroup>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAnswerSectionById(index: number, item: IAnswerSection) {
    return item.id;
  }
}

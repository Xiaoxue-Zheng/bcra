import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDisplayCondition, DisplayCondition } from 'app/shared/model/display-condition.model';
import { DisplayConditionService } from './display-condition.service';
import { IQuestionGroup } from 'app/shared/model/question-group.model';
import { QuestionGroupService } from 'app/entities/question-group';

@Component({
  selector: 'jhi-display-condition-update',
  templateUrl: './display-condition-update.component.html'
})
export class DisplayConditionUpdateComponent implements OnInit {
  isSaving: boolean;

  questiongroups: IQuestionGroup[];

  editForm = this.fb.group({
    id: [],
    questionGroupId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected displayConditionService: DisplayConditionService,
    protected questionGroupService: QuestionGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ displayCondition }) => {
      this.updateForm(displayCondition);
    });
    this.questionGroupService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IQuestionGroup[]>) => mayBeOk.ok),
        map((response: HttpResponse<IQuestionGroup[]>) => response.body)
      )
      .subscribe((res: IQuestionGroup[]) => (this.questiongroups = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(displayCondition: IDisplayCondition) {
    this.editForm.patchValue({
      id: displayCondition.id,
      questionGroupId: displayCondition.questionGroupId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const displayCondition = this.createFromForm();
    if (displayCondition.id !== undefined) {
      this.subscribeToSaveResponse(this.displayConditionService.update(displayCondition));
    } else {
      this.subscribeToSaveResponse(this.displayConditionService.create(displayCondition));
    }
  }

  private createFromForm(): IDisplayCondition {
    return {
      ...new DisplayCondition(),
      id: this.editForm.get(['id']).value,
      questionGroupId: this.editForm.get(['questionGroupId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDisplayCondition>>) {
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

  trackQuestionGroupById(index: number, item: IQuestionGroup) {
    return item.id;
  }
}

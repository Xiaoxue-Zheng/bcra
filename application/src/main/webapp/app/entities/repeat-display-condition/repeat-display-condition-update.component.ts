import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRepeatDisplayCondition, RepeatDisplayCondition } from 'app/shared/model/repeat-display-condition.model';
import { RepeatDisplayConditionService } from './repeat-display-condition.service';
import { IRepeatQuestion } from 'app/shared/model/repeat-question.model';
import { RepeatQuestionService } from 'app/entities/repeat-question';

@Component({
  selector: 'jhi-repeat-display-condition-update',
  templateUrl: './repeat-display-condition-update.component.html'
})
export class RepeatDisplayConditionUpdateComponent implements OnInit {
  isSaving: boolean;

  repeatquestions: IRepeatQuestion[];

  editForm = this.fb.group({
    id: [],
    repeatQuestionId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected repeatDisplayConditionService: RepeatDisplayConditionService,
    protected repeatQuestionService: RepeatQuestionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ repeatDisplayCondition }) => {
      this.updateForm(repeatDisplayCondition);
    });
    this.repeatQuestionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRepeatQuestion[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRepeatQuestion[]>) => response.body)
      )
      .subscribe((res: IRepeatQuestion[]) => (this.repeatquestions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(repeatDisplayCondition: IRepeatDisplayCondition) {
    this.editForm.patchValue({
      id: repeatDisplayCondition.id,
      repeatQuestionId: repeatDisplayCondition.repeatQuestionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const repeatDisplayCondition = this.createFromForm();
    if (repeatDisplayCondition.id !== undefined) {
      this.subscribeToSaveResponse(this.repeatDisplayConditionService.update(repeatDisplayCondition));
    } else {
      this.subscribeToSaveResponse(this.repeatDisplayConditionService.create(repeatDisplayCondition));
    }
  }

  private createFromForm(): IRepeatDisplayCondition {
    return {
      ...new RepeatDisplayCondition(),
      id: this.editForm.get(['id']).value,
      repeatQuestionId: this.editForm.get(['repeatQuestionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRepeatDisplayCondition>>) {
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

  trackRepeatQuestionById(index: number, item: IRepeatQuestion) {
    return item.id;
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICheckboxQuestionItem, CheckboxQuestionItem } from 'app/shared/model/checkbox-question-item.model';
import { CheckboxQuestionItemService } from './checkbox-question-item.service';
import { ICheckboxQuestion } from 'app/shared/model/checkbox-question.model';
import { CheckboxQuestionService } from 'app/entities/checkbox-question';

@Component({
  selector: 'jhi-checkbox-question-item-update',
  templateUrl: './checkbox-question-item-update.component.html'
})
export class CheckboxQuestionItemUpdateComponent implements OnInit {
  isSaving: boolean;

  checkboxquestions: ICheckboxQuestion[];

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    questionUuid: [null, [Validators.required]],
    label: [null, [Validators.required]],
    descriptor: [null, [Validators.required]],
    checkboxQuestionId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected checkboxQuestionItemService: CheckboxQuestionItemService,
    protected checkboxQuestionService: CheckboxQuestionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ checkboxQuestionItem }) => {
      this.updateForm(checkboxQuestionItem);
    });
    this.checkboxQuestionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICheckboxQuestion[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICheckboxQuestion[]>) => response.body)
      )
      .subscribe((res: ICheckboxQuestion[]) => (this.checkboxquestions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(checkboxQuestionItem: ICheckboxQuestionItem) {
    this.editForm.patchValue({
      id: checkboxQuestionItem.id,
      uuid: checkboxQuestionItem.uuid,
      questionUuid: checkboxQuestionItem.questionUuid,
      label: checkboxQuestionItem.label,
      descriptor: checkboxQuestionItem.descriptor,
      checkboxQuestionId: checkboxQuestionItem.checkboxQuestionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const checkboxQuestionItem = this.createFromForm();
    if (checkboxQuestionItem.id !== undefined) {
      this.subscribeToSaveResponse(this.checkboxQuestionItemService.update(checkboxQuestionItem));
    } else {
      this.subscribeToSaveResponse(this.checkboxQuestionItemService.create(checkboxQuestionItem));
    }
  }

  private createFromForm(): ICheckboxQuestionItem {
    return {
      ...new CheckboxQuestionItem(),
      id: this.editForm.get(['id']).value,
      uuid: this.editForm.get(['uuid']).value,
      questionUuid: this.editForm.get(['questionUuid']).value,
      label: this.editForm.get(['label']).value,
      descriptor: this.editForm.get(['descriptor']).value,
      checkboxQuestionId: this.editForm.get(['checkboxQuestionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICheckboxQuestionItem>>) {
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

  trackCheckboxQuestionById(index: number, item: ICheckboxQuestion) {
    return item.id;
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICheckboxDisplayCondition, CheckboxDisplayCondition } from 'app/shared/model/checkbox-display-condition.model';
import { CheckboxDisplayConditionService } from './checkbox-display-condition.service';
import { ICheckboxQuestionItem } from 'app/shared/model/checkbox-question-item.model';
import { CheckboxQuestionItemService } from 'app/entities/checkbox-question-item';

@Component({
  selector: 'jhi-checkbox-display-condition-update',
  templateUrl: './checkbox-display-condition-update.component.html'
})
export class CheckboxDisplayConditionUpdateComponent implements OnInit {
  isSaving: boolean;

  checkboxquestionitems: ICheckboxQuestionItem[];

  editForm = this.fb.group({
    id: [],
    checkboxQuestionItemId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected checkboxDisplayConditionService: CheckboxDisplayConditionService,
    protected checkboxQuestionItemService: CheckboxQuestionItemService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ checkboxDisplayCondition }) => {
      this.updateForm(checkboxDisplayCondition);
    });
    this.checkboxQuestionItemService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICheckboxQuestionItem[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICheckboxQuestionItem[]>) => response.body)
      )
      .subscribe(
        (res: ICheckboxQuestionItem[]) => (this.checkboxquestionitems = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(checkboxDisplayCondition: ICheckboxDisplayCondition) {
    this.editForm.patchValue({
      id: checkboxDisplayCondition.id,
      checkboxQuestionItemId: checkboxDisplayCondition.checkboxQuestionItemId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const checkboxDisplayCondition = this.createFromForm();
    if (checkboxDisplayCondition.id !== undefined) {
      this.subscribeToSaveResponse(this.checkboxDisplayConditionService.update(checkboxDisplayCondition));
    } else {
      this.subscribeToSaveResponse(this.checkboxDisplayConditionService.create(checkboxDisplayCondition));
    }
  }

  private createFromForm(): ICheckboxDisplayCondition {
    return {
      ...new CheckboxDisplayCondition(),
      id: this.editForm.get(['id']).value,
      checkboxQuestionItemId: this.editForm.get(['checkboxQuestionItemId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICheckboxDisplayCondition>>) {
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

  trackCheckboxQuestionItemById(index: number, item: ICheckboxQuestionItem) {
    return item.id;
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICheckboxAnswerItem, CheckboxAnswerItem } from 'app/shared/model/checkbox-answer-item.model';
import { CheckboxAnswerItemService } from './checkbox-answer-item.service';
import { ICheckboxAnswer } from 'app/shared/model/checkbox-answer.model';
import { CheckboxAnswerService } from 'app/entities/checkbox-answer';
import { ICheckboxQuestionItem } from 'app/shared/model/checkbox-question-item.model';
import { CheckboxQuestionItemService } from 'app/entities/checkbox-question-item';

@Component({
  selector: 'jhi-checkbox-answer-item-update',
  templateUrl: './checkbox-answer-item-update.component.html'
})
export class CheckboxAnswerItemUpdateComponent implements OnInit {
  isSaving: boolean;

  checkboxanswers: ICheckboxAnswer[];

  checkboxquestionitems: ICheckboxQuestionItem[];

  editForm = this.fb.group({
    id: [],
    checkboxAnswerId: [],
    checkboxQuestionItemId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected checkboxAnswerItemService: CheckboxAnswerItemService,
    protected checkboxAnswerService: CheckboxAnswerService,
    protected checkboxQuestionItemService: CheckboxQuestionItemService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ checkboxAnswerItem }) => {
      this.updateForm(checkboxAnswerItem);
    });
    this.checkboxAnswerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICheckboxAnswer[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICheckboxAnswer[]>) => response.body)
      )
      .subscribe((res: ICheckboxAnswer[]) => (this.checkboxanswers = res), (res: HttpErrorResponse) => this.onError(res.message));
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

  updateForm(checkboxAnswerItem: ICheckboxAnswerItem) {
    this.editForm.patchValue({
      id: checkboxAnswerItem.id,
      checkboxAnswerId: checkboxAnswerItem.checkboxAnswerId,
      checkboxQuestionItemId: checkboxAnswerItem.checkboxQuestionItemId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const checkboxAnswerItem = this.createFromForm();
    if (checkboxAnswerItem.id !== undefined) {
      this.subscribeToSaveResponse(this.checkboxAnswerItemService.update(checkboxAnswerItem));
    } else {
      this.subscribeToSaveResponse(this.checkboxAnswerItemService.create(checkboxAnswerItem));
    }
  }

  private createFromForm(): ICheckboxAnswerItem {
    return {
      ...new CheckboxAnswerItem(),
      id: this.editForm.get(['id']).value,
      checkboxAnswerId: this.editForm.get(['checkboxAnswerId']).value,
      checkboxQuestionItemId: this.editForm.get(['checkboxQuestionItemId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICheckboxAnswerItem>>) {
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

  trackCheckboxAnswerById(index: number, item: ICheckboxAnswer) {
    return item.id;
  }

  trackCheckboxQuestionItemById(index: number, item: ICheckboxQuestionItem) {
    return item.id;
  }
}

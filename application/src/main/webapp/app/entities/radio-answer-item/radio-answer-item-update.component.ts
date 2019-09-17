import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRadioAnswerItem, RadioAnswerItem } from 'app/shared/model/radio-answer-item.model';
import { RadioAnswerItemService } from './radio-answer-item.service';
import { IRadioAnswer } from 'app/shared/model/radio-answer.model';
import { RadioAnswerService } from 'app/entities/radio-answer';

@Component({
  selector: 'jhi-radio-answer-item-update',
  templateUrl: './radio-answer-item-update.component.html'
})
export class RadioAnswerItemUpdateComponent implements OnInit {
  isSaving: boolean;

  radioanswers: IRadioAnswer[];

  editForm = this.fb.group({
    id: [],
    radioAnswerId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected radioAnswerItemService: RadioAnswerItemService,
    protected radioAnswerService: RadioAnswerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ radioAnswerItem }) => {
      this.updateForm(radioAnswerItem);
    });
    this.radioAnswerService
      .query({ 'radioAnswerItemId.specified': 'false' })
      .pipe(
        filter((mayBeOk: HttpResponse<IRadioAnswer[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRadioAnswer[]>) => response.body)
      )
      .subscribe(
        (res: IRadioAnswer[]) => {
          if (!this.editForm.get('radioAnswerId').value) {
            this.radioanswers = res;
          } else {
            this.radioAnswerService
              .find(this.editForm.get('radioAnswerId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IRadioAnswer>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IRadioAnswer>) => subResponse.body)
              )
              .subscribe(
                (subRes: IRadioAnswer) => (this.radioanswers = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(radioAnswerItem: IRadioAnswerItem) {
    this.editForm.patchValue({
      id: radioAnswerItem.id,
      radioAnswerId: radioAnswerItem.radioAnswerId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const radioAnswerItem = this.createFromForm();
    if (radioAnswerItem.id !== undefined) {
      this.subscribeToSaveResponse(this.radioAnswerItemService.update(radioAnswerItem));
    } else {
      this.subscribeToSaveResponse(this.radioAnswerItemService.create(radioAnswerItem));
    }
  }

  private createFromForm(): IRadioAnswerItem {
    return {
      ...new RadioAnswerItem(),
      id: this.editForm.get(['id']).value,
      radioAnswerId: this.editForm.get(['radioAnswerId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRadioAnswerItem>>) {
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

  trackRadioAnswerById(index: number, item: IRadioAnswer) {
    return item.id;
  }
}

import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRadioQuestionItem, RadioQuestionItem } from 'app/shared/model/radio-question-item.model';
import { RadioQuestionItemService } from './radio-question-item.service';
import { IRadioQuestion } from 'app/shared/model/radio-question.model';
import { RadioQuestionService } from 'app/entities/radio-question';

@Component({
  selector: 'jhi-radio-question-item-update',
  templateUrl: './radio-question-item-update.component.html'
})
export class RadioQuestionItemUpdateComponent implements OnInit {
  isSaving: boolean;

  radioquestions: IRadioQuestion[];

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    questionUuid: [null, [Validators.required]],
    label: [null, [Validators.required]],
    descriptor: [null, [Validators.required]],
    radioQuestionId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected radioQuestionItemService: RadioQuestionItemService,
    protected radioQuestionService: RadioQuestionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ radioQuestionItem }) => {
      this.updateForm(radioQuestionItem);
    });
    this.radioQuestionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRadioQuestion[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRadioQuestion[]>) => response.body)
      )
      .subscribe((res: IRadioQuestion[]) => (this.radioquestions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(radioQuestionItem: IRadioQuestionItem) {
    this.editForm.patchValue({
      id: radioQuestionItem.id,
      uuid: radioQuestionItem.uuid,
      questionUuid: radioQuestionItem.questionUuid,
      label: radioQuestionItem.label,
      descriptor: radioQuestionItem.descriptor,
      radioQuestionId: radioQuestionItem.radioQuestionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const radioQuestionItem = this.createFromForm();
    if (radioQuestionItem.id !== undefined) {
      this.subscribeToSaveResponse(this.radioQuestionItemService.update(radioQuestionItem));
    } else {
      this.subscribeToSaveResponse(this.radioQuestionItemService.create(radioQuestionItem));
    }
  }

  private createFromForm(): IRadioQuestionItem {
    return {
      ...new RadioQuestionItem(),
      id: this.editForm.get(['id']).value,
      uuid: this.editForm.get(['uuid']).value,
      questionUuid: this.editForm.get(['questionUuid']).value,
      label: this.editForm.get(['label']).value,
      descriptor: this.editForm.get(['descriptor']).value,
      radioQuestionId: this.editForm.get(['radioQuestionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRadioQuestionItem>>) {
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

  trackRadioQuestionById(index: number, item: IRadioQuestion) {
    return item.id;
  }
}

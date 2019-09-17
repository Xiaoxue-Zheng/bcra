import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRadioAnswer, RadioAnswer } from 'app/shared/model/radio-answer.model';
import { RadioAnswerService } from './radio-answer.service';
import { IRadioAnswerItem } from 'app/shared/model/radio-answer-item.model';
import { RadioAnswerItemService } from 'app/entities/radio-answer-item';

@Component({
  selector: 'jhi-radio-answer-update',
  templateUrl: './radio-answer-update.component.html'
})
export class RadioAnswerUpdateComponent implements OnInit {
  isSaving: boolean;

  radioansweritems: IRadioAnswerItem[];

  editForm = this.fb.group({
    id: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected radioAnswerService: RadioAnswerService,
    protected radioAnswerItemService: RadioAnswerItemService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ radioAnswer }) => {
      this.updateForm(radioAnswer);
    });
    this.radioAnswerItemService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRadioAnswerItem[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRadioAnswerItem[]>) => response.body)
      )
      .subscribe((res: IRadioAnswerItem[]) => (this.radioansweritems = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(radioAnswer: IRadioAnswer) {
    this.editForm.patchValue({
      id: radioAnswer.id
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const radioAnswer = this.createFromForm();
    if (radioAnswer.id !== undefined) {
      this.subscribeToSaveResponse(this.radioAnswerService.update(radioAnswer));
    } else {
      this.subscribeToSaveResponse(this.radioAnswerService.create(radioAnswer));
    }
  }

  private createFromForm(): IRadioAnswer {
    return {
      ...new RadioAnswer(),
      id: this.editForm.get(['id']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRadioAnswer>>) {
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

  trackRadioAnswerItemById(index: number, item: IRadioAnswerItem) {
    return item.id;
  }
}

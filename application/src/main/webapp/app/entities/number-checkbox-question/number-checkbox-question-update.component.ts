import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { INumberCheckboxQuestion, NumberCheckboxQuestion } from 'app/shared/model/number-checkbox-question.model';
import { NumberCheckboxQuestionService } from './number-checkbox-question.service';

@Component({
  selector: 'jhi-number-checkbox-question-update',
  templateUrl: './number-checkbox-question-update.component.html'
})
export class NumberCheckboxQuestionUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    minimum: [null, [Validators.required]],
    maximum: [null, [Validators.required]]
  });

  constructor(
    protected numberCheckboxQuestionService: NumberCheckboxQuestionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ numberCheckboxQuestion }) => {
      this.updateForm(numberCheckboxQuestion);
    });
  }

  updateForm(numberCheckboxQuestion: INumberCheckboxQuestion) {
    this.editForm.patchValue({
      id: numberCheckboxQuestion.id,
      minimum: numberCheckboxQuestion.minimum,
      maximum: numberCheckboxQuestion.maximum
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const numberCheckboxQuestion = this.createFromForm();
    if (numberCheckboxQuestion.id !== undefined) {
      this.subscribeToSaveResponse(this.numberCheckboxQuestionService.update(numberCheckboxQuestion));
    } else {
      this.subscribeToSaveResponse(this.numberCheckboxQuestionService.create(numberCheckboxQuestion));
    }
  }

  private createFromForm(): INumberCheckboxQuestion {
    return {
      ...new NumberCheckboxQuestion(),
      id: this.editForm.get(['id']).value,
      minimum: this.editForm.get(['minimum']).value,
      maximum: this.editForm.get(['maximum']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INumberCheckboxQuestion>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}

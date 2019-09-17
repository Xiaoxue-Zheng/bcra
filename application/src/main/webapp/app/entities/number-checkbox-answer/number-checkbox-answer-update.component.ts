import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { INumberCheckboxAnswer, NumberCheckboxAnswer } from 'app/shared/model/number-checkbox-answer.model';
import { NumberCheckboxAnswerService } from './number-checkbox-answer.service';

@Component({
  selector: 'jhi-number-checkbox-answer-update',
  templateUrl: './number-checkbox-answer-update.component.html'
})
export class NumberCheckboxAnswerUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    number: [],
    check: []
  });

  constructor(
    protected numberCheckboxAnswerService: NumberCheckboxAnswerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ numberCheckboxAnswer }) => {
      this.updateForm(numberCheckboxAnswer);
    });
  }

  updateForm(numberCheckboxAnswer: INumberCheckboxAnswer) {
    this.editForm.patchValue({
      id: numberCheckboxAnswer.id,
      number: numberCheckboxAnswer.number,
      check: numberCheckboxAnswer.check
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const numberCheckboxAnswer = this.createFromForm();
    if (numberCheckboxAnswer.id !== undefined) {
      this.subscribeToSaveResponse(this.numberCheckboxAnswerService.update(numberCheckboxAnswer));
    } else {
      this.subscribeToSaveResponse(this.numberCheckboxAnswerService.create(numberCheckboxAnswer));
    }
  }

  private createFromForm(): INumberCheckboxAnswer {
    return {
      ...new NumberCheckboxAnswer(),
      id: this.editForm.get(['id']).value,
      number: this.editForm.get(['number']).value,
      check: this.editForm.get(['check']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INumberCheckboxAnswer>>) {
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

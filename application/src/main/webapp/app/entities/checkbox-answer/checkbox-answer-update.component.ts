import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICheckboxAnswer, CheckboxAnswer } from 'app/shared/model/checkbox-answer.model';
import { CheckboxAnswerService } from './checkbox-answer.service';

@Component({
  selector: 'jhi-checkbox-answer-update',
  templateUrl: './checkbox-answer-update.component.html'
})
export class CheckboxAnswerUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: []
  });

  constructor(protected checkboxAnswerService: CheckboxAnswerService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ checkboxAnswer }) => {
      this.updateForm(checkboxAnswer);
    });
  }

  updateForm(checkboxAnswer: ICheckboxAnswer) {
    this.editForm.patchValue({
      id: checkboxAnswer.id
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const checkboxAnswer = this.createFromForm();
    if (checkboxAnswer.id !== undefined) {
      this.subscribeToSaveResponse(this.checkboxAnswerService.update(checkboxAnswer));
    } else {
      this.subscribeToSaveResponse(this.checkboxAnswerService.create(checkboxAnswer));
    }
  }

  private createFromForm(): ICheckboxAnswer {
    return {
      ...new CheckboxAnswer(),
      id: this.editForm.get(['id']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICheckboxAnswer>>) {
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

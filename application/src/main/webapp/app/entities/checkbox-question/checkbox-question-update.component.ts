import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICheckboxQuestion, CheckboxQuestion } from 'app/shared/model/checkbox-question.model';
import { CheckboxQuestionService } from './checkbox-question.service';

@Component({
  selector: 'jhi-checkbox-question-update',
  templateUrl: './checkbox-question-update.component.html'
})
export class CheckboxQuestionUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: []
  });

  constructor(
    protected checkboxQuestionService: CheckboxQuestionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ checkboxQuestion }) => {
      this.updateForm(checkboxQuestion);
    });
  }

  updateForm(checkboxQuestion: ICheckboxQuestion) {
    this.editForm.patchValue({
      id: checkboxQuestion.id
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const checkboxQuestion = this.createFromForm();
    if (checkboxQuestion.id !== undefined) {
      this.subscribeToSaveResponse(this.checkboxQuestionService.update(checkboxQuestion));
    } else {
      this.subscribeToSaveResponse(this.checkboxQuestionService.create(checkboxQuestion));
    }
  }

  private createFromForm(): ICheckboxQuestion {
    return {
      ...new CheckboxQuestion(),
      id: this.editForm.get(['id']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICheckboxQuestion>>) {
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

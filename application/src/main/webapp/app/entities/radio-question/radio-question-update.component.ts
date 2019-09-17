import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IRadioQuestion, RadioQuestion } from 'app/shared/model/radio-question.model';
import { RadioQuestionService } from './radio-question.service';

@Component({
  selector: 'jhi-radio-question-update',
  templateUrl: './radio-question-update.component.html'
})
export class RadioQuestionUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: []
  });

  constructor(protected radioQuestionService: RadioQuestionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ radioQuestion }) => {
      this.updateForm(radioQuestion);
    });
  }

  updateForm(radioQuestion: IRadioQuestion) {
    this.editForm.patchValue({
      id: radioQuestion.id
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const radioQuestion = this.createFromForm();
    if (radioQuestion.id !== undefined) {
      this.subscribeToSaveResponse(this.radioQuestionService.update(radioQuestion));
    } else {
      this.subscribeToSaveResponse(this.radioQuestionService.create(radioQuestion));
    }
  }

  private createFromForm(): IRadioQuestion {
    return {
      ...new RadioQuestion(),
      id: this.editForm.get(['id']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRadioQuestion>>) {
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

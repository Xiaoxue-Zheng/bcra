import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IRepeatQuestion, RepeatQuestion } from 'app/shared/model/repeat-question.model';
import { RepeatQuestionService } from './repeat-question.service';

@Component({
  selector: 'jhi-repeat-question-update',
  templateUrl: './repeat-question-update.component.html'
})
export class RepeatQuestionUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    maximum: [null, [Validators.required]]
  });

  constructor(protected repeatQuestionService: RepeatQuestionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ repeatQuestion }) => {
      this.updateForm(repeatQuestion);
    });
  }

  updateForm(repeatQuestion: IRepeatQuestion) {
    this.editForm.patchValue({
      id: repeatQuestion.id,
      maximum: repeatQuestion.maximum
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const repeatQuestion = this.createFromForm();
    if (repeatQuestion.id !== undefined) {
      this.subscribeToSaveResponse(this.repeatQuestionService.update(repeatQuestion));
    } else {
      this.subscribeToSaveResponse(this.repeatQuestionService.create(repeatQuestion));
    }
  }

  private createFromForm(): IRepeatQuestion {
    return {
      ...new RepeatQuestion(),
      id: this.editForm.get(['id']).value,
      maximum: this.editForm.get(['maximum']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRepeatQuestion>>) {
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

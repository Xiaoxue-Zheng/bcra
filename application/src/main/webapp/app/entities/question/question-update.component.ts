import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IQuestion, Question } from 'app/shared/model/question.model';
import { QuestionService } from './question.service';

@Component({
  selector: 'jhi-question-update',
  templateUrl: './question-update.component.html'
})
export class QuestionUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    text: [null, [Validators.required]]
  });

  constructor(protected questionService: QuestionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ question }) => {
      this.updateForm(question);
    });
  }

  updateForm(question: IQuestion) {
    this.editForm.patchValue({
      id: question.id,
      uuid: question.uuid,
      text: question.text
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const question = this.createFromForm();
    if (question.id !== undefined) {
      this.subscribeToSaveResponse(this.questionService.update(question));
    } else {
      this.subscribeToSaveResponse(this.questionService.create(question));
    }
  }

  private createFromForm(): IQuestion {
    return {
      ...new Question(),
      id: this.editForm.get(['id']).value,
      uuid: this.editForm.get(['uuid']).value,
      text: this.editForm.get(['text']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestion>>) {
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

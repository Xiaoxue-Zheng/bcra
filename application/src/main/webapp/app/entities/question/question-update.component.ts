import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IQuestion, Question } from 'app/shared/model/question.model';
import { QuestionService } from './question.service';
import { IQuestionGroup } from 'app/shared/model/question-group.model';
import { QuestionGroupService } from 'app/entities/question-group/question-group.service';

@Component({
  selector: 'jhi-question-update',
  templateUrl: './question-update.component.html'
})
export class QuestionUpdateComponent implements OnInit {
  isSaving = false;
  questiongroups: IQuestionGroup[] = [];

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required]],
    type: [null, [Validators.required]],
    order: [null, [Validators.required]],
    text: [null, [Validators.required]],
    variableName: [],
    minimum: [],
    maximum: [],
    hint: [],
    hintText: [],
    questionGroupId: [null, Validators.required]
  });

  constructor(
    protected questionService: QuestionService,
    protected questionGroupService: QuestionGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ question }) => {
      this.updateForm(question);

      this.questionGroupService.query().subscribe((res: HttpResponse<IQuestionGroup[]>) => (this.questiongroups = res.body || []));
    });
  }

  updateForm(question: IQuestion): void {
    this.editForm.patchValue({
      id: question.id,
      identifier: question.identifier,
      type: question.type,
      order: question.order,
      text: question.text,
      variableName: question.variableName,
      minimum: question.minimum,
      maximum: question.maximum,
      hint: question.hint,
      hintText: question.hintText,
      questionGroupId: question.questionGroupId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
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
      id: this.editForm.get(['id'])!.value,
      identifier: this.editForm.get(['identifier'])!.value,
      type: this.editForm.get(['type'])!.value,
      order: this.editForm.get(['order'])!.value,
      text: this.editForm.get(['text'])!.value,
      variableName: this.editForm.get(['variableName'])!.value,
      minimum: this.editForm.get(['minimum'])!.value,
      maximum: this.editForm.get(['maximum'])!.value,
      hint: this.editForm.get(['hint'])!.value,
      hintText: this.editForm.get(['hintText'])!.value,
      questionGroupId: this.editForm.get(['questionGroupId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestion>>): void {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IQuestionGroup): any {
    return item.id;
  }
}

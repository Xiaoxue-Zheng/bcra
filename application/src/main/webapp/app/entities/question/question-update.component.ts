import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IQuestion, Question } from 'app/shared/model/question.model';
import { QuestionService } from './question.service';
import { IQuestionGroup } from 'app/shared/model/question-group.model';
import { QuestionGroupService } from 'app/entities/question-group';

@Component({
  selector: 'jhi-question-update',
  templateUrl: './question-update.component.html'
})
export class QuestionUpdateComponent implements OnInit {
  isSaving: boolean;

  questiongroups: IQuestionGroup[];

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
    protected jhiAlertService: JhiAlertService,
    protected questionService: QuestionService,
    protected questionGroupService: QuestionGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ question }) => {
      this.updateForm(question);
    });
    this.questionGroupService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IQuestionGroup[]>) => mayBeOk.ok),
        map((response: HttpResponse<IQuestionGroup[]>) => response.body)
      )
      .subscribe((res: IQuestionGroup[]) => (this.questiongroups = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(question: IQuestion) {
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
      identifier: this.editForm.get(['identifier']).value,
      type: this.editForm.get(['type']).value,
      order: this.editForm.get(['order']).value,
      text: this.editForm.get(['text']).value,
      variableName: this.editForm.get(['variableName']).value,
      minimum: this.editForm.get(['minimum']).value,
      maximum: this.editForm.get(['maximum']).value,
      hint: this.editForm.get(['hint']).value,
      hintText: this.editForm.get(['hintText']).value,
      questionGroupId: this.editForm.get(['questionGroupId']).value
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
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackQuestionGroupById(index: number, item: IQuestionGroup) {
    return item.id;
  }
}

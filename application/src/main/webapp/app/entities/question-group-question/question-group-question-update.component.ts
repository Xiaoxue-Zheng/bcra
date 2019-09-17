import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IQuestionGroupQuestion, QuestionGroupQuestion } from 'app/shared/model/question-group-question.model';
import { QuestionGroupQuestionService } from './question-group-question.service';
import { IQuestionGroup } from 'app/shared/model/question-group.model';
import { QuestionGroupService } from 'app/entities/question-group';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from 'app/entities/question';

@Component({
  selector: 'jhi-question-group-question-update',
  templateUrl: './question-group-question-update.component.html'
})
export class QuestionGroupQuestionUpdateComponent implements OnInit {
  isSaving: boolean;

  questiongroups: IQuestionGroup[];

  questions: IQuestion[];

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    questionGroupUuid: [null, [Validators.required]],
    questionUuid: [null, [Validators.required]],
    order: [null, [Validators.required]],
    questionGroupId: [],
    questionId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected questionGroupQuestionService: QuestionGroupQuestionService,
    protected questionGroupService: QuestionGroupService,
    protected questionService: QuestionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ questionGroupQuestion }) => {
      this.updateForm(questionGroupQuestion);
    });
    this.questionGroupService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IQuestionGroup[]>) => mayBeOk.ok),
        map((response: HttpResponse<IQuestionGroup[]>) => response.body)
      )
      .subscribe((res: IQuestionGroup[]) => (this.questiongroups = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.questionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IQuestion[]>) => mayBeOk.ok),
        map((response: HttpResponse<IQuestion[]>) => response.body)
      )
      .subscribe((res: IQuestion[]) => (this.questions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(questionGroupQuestion: IQuestionGroupQuestion) {
    this.editForm.patchValue({
      id: questionGroupQuestion.id,
      uuid: questionGroupQuestion.uuid,
      questionGroupUuid: questionGroupQuestion.questionGroupUuid,
      questionUuid: questionGroupQuestion.questionUuid,
      order: questionGroupQuestion.order,
      questionGroupId: questionGroupQuestion.questionGroupId,
      questionId: questionGroupQuestion.questionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const questionGroupQuestion = this.createFromForm();
    if (questionGroupQuestion.id !== undefined) {
      this.subscribeToSaveResponse(this.questionGroupQuestionService.update(questionGroupQuestion));
    } else {
      this.subscribeToSaveResponse(this.questionGroupQuestionService.create(questionGroupQuestion));
    }
  }

  private createFromForm(): IQuestionGroupQuestion {
    return {
      ...new QuestionGroupQuestion(),
      id: this.editForm.get(['id']).value,
      uuid: this.editForm.get(['uuid']).value,
      questionGroupUuid: this.editForm.get(['questionGroupUuid']).value,
      questionUuid: this.editForm.get(['questionUuid']).value,
      order: this.editForm.get(['order']).value,
      questionGroupId: this.editForm.get(['questionGroupId']).value,
      questionId: this.editForm.get(['questionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionGroupQuestion>>) {
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

  trackQuestionById(index: number, item: IQuestion) {
    return item.id;
  }
}

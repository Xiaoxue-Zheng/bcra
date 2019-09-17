import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAnswer, Answer } from 'app/shared/model/answer.model';
import { AnswerService } from './answer.service';
import { IAnswerGroup } from 'app/shared/model/answer-group.model';
import { AnswerGroupService } from 'app/entities/answer-group';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from 'app/entities/question';

@Component({
  selector: 'jhi-answer-update',
  templateUrl: './answer-update.component.html'
})
export class AnswerUpdateComponent implements OnInit {
  isSaving: boolean;

  answergroups: IAnswerGroup[];

  questions: IQuestion[];

  editForm = this.fb.group({
    id: [],
    answerGroupId: [],
    questionId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected answerService: AnswerService,
    protected answerGroupService: AnswerGroupService,
    protected questionService: QuestionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ answer }) => {
      this.updateForm(answer);
    });
    this.answerGroupService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAnswerGroup[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAnswerGroup[]>) => response.body)
      )
      .subscribe((res: IAnswerGroup[]) => (this.answergroups = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.questionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IQuestion[]>) => mayBeOk.ok),
        map((response: HttpResponse<IQuestion[]>) => response.body)
      )
      .subscribe((res: IQuestion[]) => (this.questions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(answer: IAnswer) {
    this.editForm.patchValue({
      id: answer.id,
      answerGroupId: answer.answerGroupId,
      questionId: answer.questionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const answer = this.createFromForm();
    if (answer.id !== undefined) {
      this.subscribeToSaveResponse(this.answerService.update(answer));
    } else {
      this.subscribeToSaveResponse(this.answerService.create(answer));
    }
  }

  private createFromForm(): IAnswer {
    return {
      ...new Answer(),
      id: this.editForm.get(['id']).value,
      answerGroupId: this.editForm.get(['answerGroupId']).value,
      questionId: this.editForm.get(['questionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnswer>>) {
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

  trackAnswerGroupById(index: number, item: IAnswerGroup) {
    return item.id;
  }

  trackQuestionById(index: number, item: IQuestion) {
    return item.id;
  }
}

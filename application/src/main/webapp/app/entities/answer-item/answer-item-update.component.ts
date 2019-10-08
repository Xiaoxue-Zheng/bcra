import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAnswerItem, AnswerItem } from 'app/shared/model/answer-item.model';
import { AnswerItemService } from './answer-item.service';
import { IAnswer } from 'app/shared/model/answer.model';
import { AnswerService } from 'app/entities/answer';
import { IQuestionItem } from 'app/shared/model/question-item.model';
import { QuestionItemService } from 'app/entities/question-item';

@Component({
  selector: 'jhi-answer-item-update',
  templateUrl: './answer-item-update.component.html'
})
export class AnswerItemUpdateComponent implements OnInit {
  isSaving: boolean;

  answers: IAnswer[];

  questionitems: IQuestionItem[];

  editForm = this.fb.group({
    id: [],
    selected: [null, [Validators.required]],
    answerId: [null, Validators.required],
    questionItemId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected answerItemService: AnswerItemService,
    protected answerService: AnswerService,
    protected questionItemService: QuestionItemService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ answerItem }) => {
      this.updateForm(answerItem);
    });
    this.answerService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAnswer[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAnswer[]>) => response.body)
      )
      .subscribe((res: IAnswer[]) => (this.answers = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.questionItemService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IQuestionItem[]>) => mayBeOk.ok),
        map((response: HttpResponse<IQuestionItem[]>) => response.body)
      )
      .subscribe((res: IQuestionItem[]) => (this.questionitems = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(answerItem: IAnswerItem) {
    this.editForm.patchValue({
      id: answerItem.id,
      selected: answerItem.selected,
      answerId: answerItem.answerId,
      questionItemId: answerItem.questionItemId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const answerItem = this.createFromForm();
    if (answerItem.id !== undefined) {
      this.subscribeToSaveResponse(this.answerItemService.update(answerItem));
    } else {
      this.subscribeToSaveResponse(this.answerItemService.create(answerItem));
    }
  }

  private createFromForm(): IAnswerItem {
    return {
      ...new AnswerItem(),
      id: this.editForm.get(['id']).value,
      selected: this.editForm.get(['selected']).value,
      answerId: this.editForm.get(['answerId']).value,
      questionItemId: this.editForm.get(['questionItemId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnswerItem>>) {
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

  trackAnswerById(index: number, item: IAnswer) {
    return item.id;
  }

  trackQuestionItemById(index: number, item: IQuestionItem) {
    return item.id;
  }
}

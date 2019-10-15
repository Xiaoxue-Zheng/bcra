import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IQuestionItem, QuestionItem } from 'app/shared/model/question-item.model';
import { QuestionItemService } from './question-item.service';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from 'app/entities/question';

@Component({
  selector: 'jhi-question-item-update',
  templateUrl: './question-item-update.component.html'
})
export class QuestionItemUpdateComponent implements OnInit {
  isSaving: boolean;

  questions: IQuestion[];

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required]],
    order: [null, [Validators.required]],
    label: [null, [Validators.required]],
    necessary: [],
    exclusive: [],
    questionId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected questionItemService: QuestionItemService,
    protected questionService: QuestionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ questionItem }) => {
      this.updateForm(questionItem);
    });
    this.questionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IQuestion[]>) => mayBeOk.ok),
        map((response: HttpResponse<IQuestion[]>) => response.body)
      )
      .subscribe((res: IQuestion[]) => (this.questions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(questionItem: IQuestionItem) {
    this.editForm.patchValue({
      id: questionItem.id,
      identifier: questionItem.identifier,
      order: questionItem.order,
      label: questionItem.label,
      necessary: questionItem.necessary,
      exclusive: questionItem.exclusive,
      questionId: questionItem.questionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const questionItem = this.createFromForm();
    if (questionItem.id !== undefined) {
      this.subscribeToSaveResponse(this.questionItemService.update(questionItem));
    } else {
      this.subscribeToSaveResponse(this.questionItemService.create(questionItem));
    }
  }

  private createFromForm(): IQuestionItem {
    return {
      ...new QuestionItem(),
      id: this.editForm.get(['id']).value,
      identifier: this.editForm.get(['identifier']).value,
      order: this.editForm.get(['order']).value,
      label: this.editForm.get(['label']).value,
      necessary: this.editForm.get(['necessary']).value,
      exclusive: this.editForm.get(['exclusive']).value,
      questionId: this.editForm.get(['questionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionItem>>) {
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

  trackQuestionById(index: number, item: IQuestion) {
    return item.id;
  }
}

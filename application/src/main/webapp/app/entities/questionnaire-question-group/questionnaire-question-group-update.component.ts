import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IQuestionnaireQuestionGroup, QuestionnaireQuestionGroup } from 'app/shared/model/questionnaire-question-group.model';
import { QuestionnaireQuestionGroupService } from './questionnaire-question-group.service';
import { IQuestionnaire } from 'app/shared/model/questionnaire.model';
import { QuestionnaireService } from 'app/entities/questionnaire';
import { IQuestionGroup } from 'app/shared/model/question-group.model';
import { QuestionGroupService } from 'app/entities/question-group';

@Component({
  selector: 'jhi-questionnaire-question-group-update',
  templateUrl: './questionnaire-question-group-update.component.html'
})
export class QuestionnaireQuestionGroupUpdateComponent implements OnInit {
  isSaving: boolean;

  questionnaires: IQuestionnaire[];

  questiongroups: IQuestionGroup[];

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]],
    questionnaireUuid: [null, [Validators.required]],
    questionGroupUuid: [null, [Validators.required]],
    order: [null, [Validators.required]],
    questionnaireId: [],
    questionGroupId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected questionnaireQuestionGroupService: QuestionnaireQuestionGroupService,
    protected questionnaireService: QuestionnaireService,
    protected questionGroupService: QuestionGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ questionnaireQuestionGroup }) => {
      this.updateForm(questionnaireQuestionGroup);
    });
    this.questionnaireService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IQuestionnaire[]>) => mayBeOk.ok),
        map((response: HttpResponse<IQuestionnaire[]>) => response.body)
      )
      .subscribe((res: IQuestionnaire[]) => (this.questionnaires = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.questionGroupService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IQuestionGroup[]>) => mayBeOk.ok),
        map((response: HttpResponse<IQuestionGroup[]>) => response.body)
      )
      .subscribe((res: IQuestionGroup[]) => (this.questiongroups = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(questionnaireQuestionGroup: IQuestionnaireQuestionGroup) {
    this.editForm.patchValue({
      id: questionnaireQuestionGroup.id,
      uuid: questionnaireQuestionGroup.uuid,
      questionnaireUuid: questionnaireQuestionGroup.questionnaireUuid,
      questionGroupUuid: questionnaireQuestionGroup.questionGroupUuid,
      order: questionnaireQuestionGroup.order,
      questionnaireId: questionnaireQuestionGroup.questionnaireId,
      questionGroupId: questionnaireQuestionGroup.questionGroupId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const questionnaireQuestionGroup = this.createFromForm();
    if (questionnaireQuestionGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.questionnaireQuestionGroupService.update(questionnaireQuestionGroup));
    } else {
      this.subscribeToSaveResponse(this.questionnaireQuestionGroupService.create(questionnaireQuestionGroup));
    }
  }

  private createFromForm(): IQuestionnaireQuestionGroup {
    return {
      ...new QuestionnaireQuestionGroup(),
      id: this.editForm.get(['id']).value,
      uuid: this.editForm.get(['uuid']).value,
      questionnaireUuid: this.editForm.get(['questionnaireUuid']).value,
      questionGroupUuid: this.editForm.get(['questionGroupUuid']).value,
      order: this.editForm.get(['order']).value,
      questionnaireId: this.editForm.get(['questionnaireId']).value,
      questionGroupId: this.editForm.get(['questionGroupId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionnaireQuestionGroup>>) {
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

  trackQuestionnaireById(index: number, item: IQuestionnaire) {
    return item.id;
  }

  trackQuestionGroupById(index: number, item: IQuestionGroup) {
    return item.id;
  }
}

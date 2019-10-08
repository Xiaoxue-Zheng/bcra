import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IQuestionSection, QuestionSection } from 'app/shared/model/question-section.model';
import { QuestionSectionService } from './question-section.service';
import { IQuestionnaire } from 'app/shared/model/questionnaire.model';
import { QuestionnaireService } from 'app/entities/questionnaire';
import { IQuestionGroup } from 'app/shared/model/question-group.model';
import { QuestionGroupService } from 'app/entities/question-group';

@Component({
  selector: 'jhi-question-section-update',
  templateUrl: './question-section-update.component.html'
})
export class QuestionSectionUpdateComponent implements OnInit {
  isSaving: boolean;

  questionnaires: IQuestionnaire[];

  questiongroups: IQuestionGroup[];

  editForm = this.fb.group({
    id: [],
    identifier: [null, [Validators.required]],
    order: [null, [Validators.required]],
    relativeName: [],
    questionnaireId: [null, Validators.required],
    questionGroupId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected questionSectionService: QuestionSectionService,
    protected questionnaireService: QuestionnaireService,
    protected questionGroupService: QuestionGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ questionSection }) => {
      this.updateForm(questionSection);
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

  updateForm(questionSection: IQuestionSection) {
    this.editForm.patchValue({
      id: questionSection.id,
      identifier: questionSection.identifier,
      order: questionSection.order,
      relativeName: questionSection.relativeName,
      questionnaireId: questionSection.questionnaireId,
      questionGroupId: questionSection.questionGroupId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const questionSection = this.createFromForm();
    if (questionSection.id !== undefined) {
      this.subscribeToSaveResponse(this.questionSectionService.update(questionSection));
    } else {
      this.subscribeToSaveResponse(this.questionSectionService.create(questionSection));
    }
  }

  private createFromForm(): IQuestionSection {
    return {
      ...new QuestionSection(),
      id: this.editForm.get(['id']).value,
      identifier: this.editForm.get(['identifier']).value,
      order: this.editForm.get(['order']).value,
      relativeName: this.editForm.get(['relativeName']).value,
      questionnaireId: this.editForm.get(['questionnaireId']).value,
      questionGroupId: this.editForm.get(['questionGroupId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionSection>>) {
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

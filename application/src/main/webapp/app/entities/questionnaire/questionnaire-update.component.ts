import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IQuestionnaire, Questionnaire } from 'app/shared/model/questionnaire.model';
import { QuestionnaireService } from './questionnaire.service';

@Component({
  selector: 'jhi-questionnaire-update',
  templateUrl: './questionnaire-update.component.html'
})
export class QuestionnaireUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    uuid: [null, [Validators.required]]
  });

  constructor(protected questionnaireService: QuestionnaireService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ questionnaire }) => {
      this.updateForm(questionnaire);
    });
  }

  updateForm(questionnaire: IQuestionnaire) {
    this.editForm.patchValue({
      id: questionnaire.id,
      uuid: questionnaire.uuid
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const questionnaire = this.createFromForm();
    if (questionnaire.id !== undefined) {
      this.subscribeToSaveResponse(this.questionnaireService.update(questionnaire));
    } else {
      this.subscribeToSaveResponse(this.questionnaireService.create(questionnaire));
    }
  }

  private createFromForm(): IQuestionnaire {
    return {
      ...new Questionnaire(),
      id: this.editForm.get(['id']).value,
      uuid: this.editForm.get(['uuid']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionnaire>>) {
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

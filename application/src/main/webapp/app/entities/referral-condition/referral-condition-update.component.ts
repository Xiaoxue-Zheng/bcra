import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IReferralCondition, ReferralCondition } from 'app/shared/model/referral-condition.model';
import { ReferralConditionService } from './referral-condition.service';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from 'app/entities/question';

@Component({
  selector: 'jhi-referral-condition-update',
  templateUrl: './referral-condition-update.component.html'
})
export class ReferralConditionUpdateComponent implements OnInit {
  isSaving: boolean;

  questions: IQuestion[];

  editForm = this.fb.group({
    id: [],
    andGroup: [],
    type: [null, [Validators.required]],
    questionIdentifier: [],
    itemIdentifier: [],
    number: [],
    questionId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected referralConditionService: ReferralConditionService,
    protected questionService: QuestionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ referralCondition }) => {
      this.updateForm(referralCondition);
    });
    this.questionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IQuestion[]>) => mayBeOk.ok),
        map((response: HttpResponse<IQuestion[]>) => response.body)
      )
      .subscribe((res: IQuestion[]) => (this.questions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(referralCondition: IReferralCondition) {
    this.editForm.patchValue({
      id: referralCondition.id,
      andGroup: referralCondition.andGroup,
      type: referralCondition.type,
      questionIdentifier: referralCondition.questionIdentifier,
      itemIdentifier: referralCondition.itemIdentifier,
      number: referralCondition.number,
      questionId: referralCondition.questionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const referralCondition = this.createFromForm();
    if (referralCondition.id !== undefined) {
      this.subscribeToSaveResponse(this.referralConditionService.update(referralCondition));
    } else {
      this.subscribeToSaveResponse(this.referralConditionService.create(referralCondition));
    }
  }

  private createFromForm(): IReferralCondition {
    return {
      ...new ReferralCondition(),
      id: this.editForm.get(['id']).value,
      andGroup: this.editForm.get(['andGroup']).value,
      type: this.editForm.get(['type']).value,
      questionIdentifier: this.editForm.get(['questionIdentifier']).value,
      itemIdentifier: this.editForm.get(['itemIdentifier']).value,
      number: this.editForm.get(['number']).value,
      questionId: this.editForm.get(['questionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReferralCondition>>) {
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

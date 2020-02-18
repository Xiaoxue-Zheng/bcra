import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IReferralCondition, ReferralCondition } from 'app/shared/model/referral-condition.model';
import { ReferralConditionService } from './referral-condition.service';
import { IQuestionSection } from 'app/shared/model/question-section.model';
import { QuestionSectionService } from 'app/entities/question-section/question-section.service';

@Component({
  selector: 'jhi-referral-condition-update',
  templateUrl: './referral-condition-update.component.html'
})
export class ReferralConditionUpdateComponent implements OnInit {
  isSaving = false;
  questionsections: IQuestionSection[] = [];

  editForm = this.fb.group({
    id: [],
    andGroup: [],
    type: [null, [Validators.required]],
    questionIdentifier: [],
    itemIdentifier: [],
    number: [],
    reason: [],
    questionSectionId: []
  });

  constructor(
    protected referralConditionService: ReferralConditionService,
    protected questionSectionService: QuestionSectionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ referralCondition }) => {
      this.updateForm(referralCondition);

      this.questionSectionService.query().subscribe((res: HttpResponse<IQuestionSection[]>) => (this.questionsections = res.body || []));
    });
  }

  updateForm(referralCondition: IReferralCondition): void {
    this.editForm.patchValue({
      id: referralCondition.id,
      andGroup: referralCondition.andGroup,
      type: referralCondition.type,
      questionIdentifier: referralCondition.questionIdentifier,
      itemIdentifier: referralCondition.itemIdentifier,
      number: referralCondition.number,
      reason: referralCondition.reason,
      questionSectionId: referralCondition.questionSectionId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
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
      id: this.editForm.get(['id'])!.value,
      andGroup: this.editForm.get(['andGroup'])!.value,
      type: this.editForm.get(['type'])!.value,
      questionIdentifier: this.editForm.get(['questionIdentifier'])!.value,
      itemIdentifier: this.editForm.get(['itemIdentifier'])!.value,
      number: this.editForm.get(['number'])!.value,
      reason: this.editForm.get(['reason'])!.value,
      questionSectionId: this.editForm.get(['questionSectionId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReferralCondition>>): void {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IQuestionSection): any {
    return item.id;
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStudyId } from 'app/shared/model/study-id.model';

@Component({
  selector: 'jhi-study-id-detail',
  templateUrl: './study-id-detail.component.html'
})
export class StudyIdDetailComponent implements OnInit {
  studyId: IStudyId | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ studyId }) => (this.studyId = studyId));
  }

  previousState(): void {
    window.history.back();
  }
}

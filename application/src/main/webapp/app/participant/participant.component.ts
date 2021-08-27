import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { filter } from 'rxjs/operators';
import { IParticipant } from 'app/shared/model/participant.model';
import { ParticipantService } from './participant.service';
import { CanRiskReportService } from '../can-risk-report/can-risk-report.service';
import { JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import { ITEMS_PER_PAGE } from 'app/shared';
import { ActivatedRoute, Router } from '@angular/router';
import filters from './participant.filters.json';
import orders from './participant.orders.json';

@Component({
  selector: 'jhi-participant',
  templateUrl: './participant.component.html',
  styleUrls: ['./participant.component.scss']
})
export class ParticipantComponent implements OnInit {
  participants: IParticipant[];
  itemsPerPage: any;
  routeData: any;
  page: any;
  previousPage: any;
  links: any;
  totalItems: any;
  showDetails: any;

  searchForm = this.fb.group({
    studyId: [null],
    status: [null],
    dateOfBirth: [null]
  });

  constructor(
    private participantService: ParticipantService,
    private canRiskReportService: CanRiskReportService,
    protected jhiAlertService: JhiAlertService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected parseLinks: JhiParseLinks,
    private fb: FormBuilder
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
    });
  }

  ngOnInit() {
    this.page = 0;
    this.transition();
  }

  loadParticipants() {
    this.participantService
      .query(this.getParameters())
      .pipe(filter((res: HttpResponse<IParticipant[]>) => res.ok))
      .subscribe(
        (res: HttpResponse<IParticipant[]>) => this.paginateParticipants(res.body, res.headers),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  clear() {
    this.searchForm.patchValue({
      studyId: null,
      dateOfBirth: null,
      status: null
    });
  }

  private getParameters() {
    return {
      page: this.page - 1,
      size: this.itemsPerPage,
      'studyId.equals': null == this.searchForm.get(['studyId']).value ? '' : this.searchForm.get(['studyId']).value,
      'dateOfBirth.equals': null == this.searchForm.get(['dateOfBirth']).value ? '' : this.searchForm.get(['dateOfBirth']).value,
      'status.equals':
        null == this.searchForm.get(['status']).value || 'null' === this.searchForm.get(['status']).value
          ? ''
          : this.searchForm.get(['status']).value
    };
  }

  private transition() {
    this.router.navigate(['/participants'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage
      }
    });
    this.loadParticipants();
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  private paginateParticipants(data: IParticipant[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.participants = data;
  }

  viewCanRiskReport(selectedCanRiskReportId) {
    this.canRiskReportService.viewCanRiskReport(selectedCanRiskReportId);
  }
}

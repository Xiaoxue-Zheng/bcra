import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ICanRiskReport } from 'app/shared/model/can-risk-report.model';
import { Subscription } from 'rxjs';
import { CanRiskReportService } from './can-risk-report.service';
import { filter, map } from 'rxjs/operators';

@Component({
  selector: 'jhi-can-risk-report',
  templateUrl: './can-risk-report.component.html',
  styleUrls: ['./can-risk-report.component.scss']
})
export class CanRiskReportComponent implements OnInit {
  canRiskReports?: ICanRiskReport[];
  eventSubscriber?: Subscription;
  page = 1;
  pageSize = 10;
  totalCanRiskReports?: number;

  constructor(protected canRiskReportService: CanRiskReportService, protected http: HttpClient) {}

  initialiseCanRiskReports(): Subscription {
    return this.canRiskReportService.countCanRiskReports().subscribe(response => {
      this.totalCanRiskReports = response.body;
      this.loadPage();
    });
  }

  loadPage(): void {
    this.canRiskReportService
      .findAll(this.page - 1, this.pageSize)
      .pipe(
        filter((res: HttpResponse<ICanRiskReport[]>) => res.ok),
        map((res: HttpResponse<ICanRiskReport[]>) => res.body)
      )
      .subscribe(
        (res: ICanRiskReport[]) => {
          this.canRiskReports = res;
        },
        (res: HttpErrorResponse) => {
          this.onError(res.message);
        }
      );
  }

  ngOnInit(): void {
    this.initialiseCanRiskReports();
  }

  onError(message) {
    console.error(message);
  }

  trackId(index: number, item: ICanRiskReport): number {
    return item.id!;
  }

  viewCanRiskReport(selectedCanRiskReportId) {
    this.canRiskReportService.viewCanRiskReport(selectedCanRiskReportId);
  }
}

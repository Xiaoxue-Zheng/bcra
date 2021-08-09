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
    this.canRiskReportService.getCanRiskReportById(selectedCanRiskReportId).subscribe(canRiskReportResponse => {
      const decodedData = this.base64StringToArrayBuffer(canRiskReportResponse.body.fileData);
      const file = new Blob([decodedData], { type: 'application/pdf' });
      const fileURL = URL.createObjectURL(file);
      window.open(fileURL);
    });
  }

  base64StringToArrayBuffer(base64String) {
    const input = base64String.substring(base64String.indexOf(',') + 1);
    const binaryString = window.atob(input);
    const binaryLen = binaryString.length;
    const bytes = new Uint8Array(binaryLen);
    for (let i = 0; i < binaryLen; i++) {
      const ascii = binaryString.charCodeAt(i);
      bytes[i] = ascii;
    }
    return bytes;
  }
}

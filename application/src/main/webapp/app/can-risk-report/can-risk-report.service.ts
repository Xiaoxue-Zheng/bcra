import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICanRiskReport } from 'app/shared/model/can-risk-report.model';

type EntityResponseType = HttpResponse<ICanRiskReport>;
type EntityArrayResponseType = HttpResponse<ICanRiskReport[]>;

@Injectable({ providedIn: 'root' })
export class CanRiskReportService {
  public resourceUrl = SERVER_API_URL + 'api/can-risk-reports';

  constructor(protected http: HttpClient) {}

  getCanRiskReportById(id: number) {
    const httpParams = new HttpParams().set('canRiskReportId', id.toString());
    return this.http.get<ICanRiskReport>('api/can-risk-report', { params: httpParams, observe: 'response' });
  }

  findAll(page: number, pageSize: number): Observable<EntityArrayResponseType> {
    const httpParams = new HttpParams().set('page', page.toString()).set('size', pageSize.toString());
    return this.http.get<ICanRiskReport[]>(this.resourceUrl, { params: httpParams, observe: 'response' });
  }

  countCanRiskReports() {
    return this.http.get<number>(this.resourceUrl + '/count', { observe: 'response' });
  }

  isStudyIdAvailable(studyCode: string) {
    const httpParams = new HttpParams().set('studyCode', studyCode);
    return this.http.get<boolean>(this.resourceUrl + '/study-id', { params: httpParams, observe: 'response' });
  }

  uploadCanRiskReportFile(canRiskReportFile: any): Observable<HttpResponse<void>> {
    const formData = new FormData();
    formData.append('file', canRiskReportFile.file);
    return this.http.post<void>(this.resourceUrl, formData, { observe: 'response' });
  }

  viewCanRiskReport(selectedCanRiskReportId) {
    this.getCanRiskReportById(selectedCanRiskReportId).subscribe(canRiskReportResponse => {
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

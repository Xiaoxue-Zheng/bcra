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
    const params = new HttpParams().set('canRiskReportId', id.toString());
    return this.http.get<ICanRiskReport>('api/can-risk-report', { params: params, observe: 'response' });
  }

  findAll(page: number, pageSize: number): Observable<EntityArrayResponseType> {
    const params = new HttpParams().set('page', page.toString()).set('size', pageSize.toString());
    return this.http.get<ICanRiskReport[]>(this.resourceUrl, { params: params, observe: 'response' });
  }

  countCanRiskReports() {
    return this.http.get<number>(this.resourceUrl + '/count', { observe: 'response' });
  }

  isStudyIdAvailable(studyCode: string) {
    const params = new HttpParams().set('studyCode', studyCode);
    return this.http.get<boolean>(this.resourceUrl + '/study-id', { params: params, observe: 'response' });
  }

  uploadCanRiskReportFile(canRiskReportFile: any): Observable<HttpResponse<void>> {
    const formData = new FormData();
    formData.append('file', canRiskReportFile.file);
    return this.http.post<void>(this.resourceUrl, formData, { observe: 'response' });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRiskAssessmentResult } from 'app/shared/model/risk-assessment-result.model';

type EntityResponseType = HttpResponse<IRiskAssessmentResult>;
type EntityArrayResponseType = HttpResponse<IRiskAssessmentResult[]>;

@Injectable({ providedIn: 'root' })
export class RiskAssessmentResultService {
  public resourceUrl = SERVER_API_URL + 'api/risk-assessment-results';

  constructor(protected http: HttpClient) {}

  create(riskAssessmentResult: IRiskAssessmentResult): Observable<EntityResponseType> {
    return this.http.post<IRiskAssessmentResult>(this.resourceUrl, riskAssessmentResult, { observe: 'response' });
  }

  update(riskAssessmentResult: IRiskAssessmentResult): Observable<EntityResponseType> {
    return this.http.put<IRiskAssessmentResult>(this.resourceUrl, riskAssessmentResult, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRiskAssessmentResult>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRiskAssessmentResult[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

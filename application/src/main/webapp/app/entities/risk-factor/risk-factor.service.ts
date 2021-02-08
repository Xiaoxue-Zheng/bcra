import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRiskFactor } from 'app/shared/model/risk-factor.model';

type EntityResponseType = HttpResponse<IRiskFactor>;
type EntityArrayResponseType = HttpResponse<IRiskFactor[]>;

@Injectable({ providedIn: 'root' })
export class RiskFactorService {
  public resourceUrl = SERVER_API_URL + 'api/risk-factors';

  constructor(protected http: HttpClient) {}

  create(riskFactor: IRiskFactor): Observable<EntityResponseType> {
    return this.http.post<IRiskFactor>(this.resourceUrl, riskFactor, { observe: 'response' });
  }

  update(riskFactor: IRiskFactor): Observable<EntityResponseType> {
    return this.http.put<IRiskFactor>(this.resourceUrl, riskFactor, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRiskFactor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRiskFactor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

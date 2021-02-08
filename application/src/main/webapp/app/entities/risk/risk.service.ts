import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IRisk } from 'app/shared/model/risk.model';

type EntityResponseType = HttpResponse<IRisk>;
type EntityArrayResponseType = HttpResponse<IRisk[]>;

@Injectable({ providedIn: 'root' })
export class RiskService {
  public resourceUrl = SERVER_API_URL + 'api/risks';

  constructor(protected http: HttpClient) {}

  create(risk: IRisk): Observable<EntityResponseType> {
    return this.http.post<IRisk>(this.resourceUrl, risk, { observe: 'response' });
  }

  update(risk: IRisk): Observable<EntityResponseType> {
    return this.http.put<IRisk>(this.resourceUrl, risk, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRisk>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRisk[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

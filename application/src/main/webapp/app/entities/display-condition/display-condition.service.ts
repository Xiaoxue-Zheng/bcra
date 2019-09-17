import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDisplayCondition } from 'app/shared/model/display-condition.model';

type EntityResponseType = HttpResponse<IDisplayCondition>;
type EntityArrayResponseType = HttpResponse<IDisplayCondition[]>;

@Injectable({ providedIn: 'root' })
export class DisplayConditionService {
  public resourceUrl = SERVER_API_URL + 'api/display-conditions';

  constructor(protected http: HttpClient) {}

  create(displayCondition: IDisplayCondition): Observable<EntityResponseType> {
    return this.http.post<IDisplayCondition>(this.resourceUrl, displayCondition, { observe: 'response' });
  }

  update(displayCondition: IDisplayCondition): Observable<EntityResponseType> {
    return this.http.put<IDisplayCondition>(this.resourceUrl, displayCondition, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDisplayCondition>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDisplayCondition[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

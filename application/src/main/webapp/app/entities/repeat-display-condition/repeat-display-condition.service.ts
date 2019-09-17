import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRepeatDisplayCondition } from 'app/shared/model/repeat-display-condition.model';

type EntityResponseType = HttpResponse<IRepeatDisplayCondition>;
type EntityArrayResponseType = HttpResponse<IRepeatDisplayCondition[]>;

@Injectable({ providedIn: 'root' })
export class RepeatDisplayConditionService {
  public resourceUrl = SERVER_API_URL + 'api/repeat-display-conditions';

  constructor(protected http: HttpClient) {}

  create(repeatDisplayCondition: IRepeatDisplayCondition): Observable<EntityResponseType> {
    return this.http.post<IRepeatDisplayCondition>(this.resourceUrl, repeatDisplayCondition, { observe: 'response' });
  }

  update(repeatDisplayCondition: IRepeatDisplayCondition): Observable<EntityResponseType> {
    return this.http.put<IRepeatDisplayCondition>(this.resourceUrl, repeatDisplayCondition, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRepeatDisplayCondition>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRepeatDisplayCondition[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

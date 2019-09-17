import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRepeatAnswer } from 'app/shared/model/repeat-answer.model';

type EntityResponseType = HttpResponse<IRepeatAnswer>;
type EntityArrayResponseType = HttpResponse<IRepeatAnswer[]>;

@Injectable({ providedIn: 'root' })
export class RepeatAnswerService {
  public resourceUrl = SERVER_API_URL + 'api/repeat-answers';

  constructor(protected http: HttpClient) {}

  create(repeatAnswer: IRepeatAnswer): Observable<EntityResponseType> {
    return this.http.post<IRepeatAnswer>(this.resourceUrl, repeatAnswer, { observe: 'response' });
  }

  update(repeatAnswer: IRepeatAnswer): Observable<EntityResponseType> {
    return this.http.put<IRepeatAnswer>(this.resourceUrl, repeatAnswer, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRepeatAnswer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRepeatAnswer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

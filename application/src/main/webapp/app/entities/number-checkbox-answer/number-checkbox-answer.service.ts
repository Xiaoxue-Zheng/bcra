import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INumberCheckboxAnswer } from 'app/shared/model/number-checkbox-answer.model';

type EntityResponseType = HttpResponse<INumberCheckboxAnswer>;
type EntityArrayResponseType = HttpResponse<INumberCheckboxAnswer[]>;

@Injectable({ providedIn: 'root' })
export class NumberCheckboxAnswerService {
  public resourceUrl = SERVER_API_URL + 'api/number-checkbox-answers';

  constructor(protected http: HttpClient) {}

  create(numberCheckboxAnswer: INumberCheckboxAnswer): Observable<EntityResponseType> {
    return this.http.post<INumberCheckboxAnswer>(this.resourceUrl, numberCheckboxAnswer, { observe: 'response' });
  }

  update(numberCheckboxAnswer: INumberCheckboxAnswer): Observable<EntityResponseType> {
    return this.http.put<INumberCheckboxAnswer>(this.resourceUrl, numberCheckboxAnswer, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INumberCheckboxAnswer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INumberCheckboxAnswer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

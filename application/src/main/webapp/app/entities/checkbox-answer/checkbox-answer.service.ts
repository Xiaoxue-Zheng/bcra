import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICheckboxAnswer } from 'app/shared/model/checkbox-answer.model';

type EntityResponseType = HttpResponse<ICheckboxAnswer>;
type EntityArrayResponseType = HttpResponse<ICheckboxAnswer[]>;

@Injectable({ providedIn: 'root' })
export class CheckboxAnswerService {
  public resourceUrl = SERVER_API_URL + 'api/checkbox-answers';

  constructor(protected http: HttpClient) {}

  create(checkboxAnswer: ICheckboxAnswer): Observable<EntityResponseType> {
    return this.http.post<ICheckboxAnswer>(this.resourceUrl, checkboxAnswer, { observe: 'response' });
  }

  update(checkboxAnswer: ICheckboxAnswer): Observable<EntityResponseType> {
    return this.http.put<ICheckboxAnswer>(this.resourceUrl, checkboxAnswer, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICheckboxAnswer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICheckboxAnswer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

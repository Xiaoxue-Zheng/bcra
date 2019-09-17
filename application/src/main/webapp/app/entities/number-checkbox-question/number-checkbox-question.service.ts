import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INumberCheckboxQuestion } from 'app/shared/model/number-checkbox-question.model';

type EntityResponseType = HttpResponse<INumberCheckboxQuestion>;
type EntityArrayResponseType = HttpResponse<INumberCheckboxQuestion[]>;

@Injectable({ providedIn: 'root' })
export class NumberCheckboxQuestionService {
  public resourceUrl = SERVER_API_URL + 'api/number-checkbox-questions';

  constructor(protected http: HttpClient) {}

  create(numberCheckboxQuestion: INumberCheckboxQuestion): Observable<EntityResponseType> {
    return this.http.post<INumberCheckboxQuestion>(this.resourceUrl, numberCheckboxQuestion, { observe: 'response' });
  }

  update(numberCheckboxQuestion: INumberCheckboxQuestion): Observable<EntityResponseType> {
    return this.http.put<INumberCheckboxQuestion>(this.resourceUrl, numberCheckboxQuestion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INumberCheckboxQuestion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INumberCheckboxQuestion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

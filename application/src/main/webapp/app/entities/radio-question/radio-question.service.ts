import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRadioQuestion } from 'app/shared/model/radio-question.model';

type EntityResponseType = HttpResponse<IRadioQuestion>;
type EntityArrayResponseType = HttpResponse<IRadioQuestion[]>;

@Injectable({ providedIn: 'root' })
export class RadioQuestionService {
  public resourceUrl = SERVER_API_URL + 'api/radio-questions';

  constructor(protected http: HttpClient) {}

  create(radioQuestion: IRadioQuestion): Observable<EntityResponseType> {
    return this.http.post<IRadioQuestion>(this.resourceUrl, radioQuestion, { observe: 'response' });
  }

  update(radioQuestion: IRadioQuestion): Observable<EntityResponseType> {
    return this.http.put<IRadioQuestion>(this.resourceUrl, radioQuestion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRadioQuestion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRadioQuestion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

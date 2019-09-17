import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRepeatQuestion } from 'app/shared/model/repeat-question.model';

type EntityResponseType = HttpResponse<IRepeatQuestion>;
type EntityArrayResponseType = HttpResponse<IRepeatQuestion[]>;

@Injectable({ providedIn: 'root' })
export class RepeatQuestionService {
  public resourceUrl = SERVER_API_URL + 'api/repeat-questions';

  constructor(protected http: HttpClient) {}

  create(repeatQuestion: IRepeatQuestion): Observable<EntityResponseType> {
    return this.http.post<IRepeatQuestion>(this.resourceUrl, repeatQuestion, { observe: 'response' });
  }

  update(repeatQuestion: IRepeatQuestion): Observable<EntityResponseType> {
    return this.http.put<IRepeatQuestion>(this.resourceUrl, repeatQuestion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRepeatQuestion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRepeatQuestion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

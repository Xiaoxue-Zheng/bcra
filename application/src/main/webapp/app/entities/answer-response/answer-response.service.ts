import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAnswerResponse } from 'app/shared/model/answer-response.model';

type EntityResponseType = HttpResponse<IAnswerResponse>;
type EntityArrayResponseType = HttpResponse<IAnswerResponse[]>;

@Injectable({ providedIn: 'root' })
export class AnswerResponseService {
  public resourceUrl = SERVER_API_URL + 'api/answer-responses';

  constructor(protected http: HttpClient) {}

  create(answerResponse: IAnswerResponse): Observable<EntityResponseType> {
    return this.http.post<IAnswerResponse>(this.resourceUrl, answerResponse, { observe: 'response' });
  }

  update(answerResponse: IAnswerResponse): Observable<EntityResponseType> {
    return this.http.put<IAnswerResponse>(this.resourceUrl, answerResponse, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnswerResponse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnswerResponse[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

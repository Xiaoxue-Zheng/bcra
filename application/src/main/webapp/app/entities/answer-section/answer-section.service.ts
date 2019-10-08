import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAnswerSection } from 'app/shared/model/answer-section.model';

type EntityResponseType = HttpResponse<IAnswerSection>;
type EntityArrayResponseType = HttpResponse<IAnswerSection[]>;

@Injectable({ providedIn: 'root' })
export class AnswerSectionService {
  public resourceUrl = SERVER_API_URL + 'api/answer-sections';

  constructor(protected http: HttpClient) {}

  create(answerSection: IAnswerSection): Observable<EntityResponseType> {
    return this.http.post<IAnswerSection>(this.resourceUrl, answerSection, { observe: 'response' });
  }

  update(answerSection: IAnswerSection): Observable<EntityResponseType> {
    return this.http.put<IAnswerSection>(this.resourceUrl, answerSection, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnswerSection>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnswerSection[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

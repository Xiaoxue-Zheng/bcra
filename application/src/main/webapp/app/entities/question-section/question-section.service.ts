import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IQuestionSection } from 'app/shared/model/question-section.model';

type EntityResponseType = HttpResponse<IQuestionSection>;
type EntityArrayResponseType = HttpResponse<IQuestionSection[]>;

@Injectable({ providedIn: 'root' })
export class QuestionSectionService {
  public resourceUrl = SERVER_API_URL + 'api/question-sections';

  constructor(protected http: HttpClient) {}

  create(questionSection: IQuestionSection): Observable<EntityResponseType> {
    return this.http.post<IQuestionSection>(this.resourceUrl, questionSection, { observe: 'response' });
  }

  update(questionSection: IQuestionSection): Observable<EntityResponseType> {
    return this.http.put<IQuestionSection>(this.resourceUrl, questionSection, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQuestionSection>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQuestionSection[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

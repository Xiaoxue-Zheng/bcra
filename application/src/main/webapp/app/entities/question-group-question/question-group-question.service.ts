import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQuestionGroupQuestion } from 'app/shared/model/question-group-question.model';

type EntityResponseType = HttpResponse<IQuestionGroupQuestion>;
type EntityArrayResponseType = HttpResponse<IQuestionGroupQuestion[]>;

@Injectable({ providedIn: 'root' })
export class QuestionGroupQuestionService {
  public resourceUrl = SERVER_API_URL + 'api/question-group-questions';

  constructor(protected http: HttpClient) {}

  create(questionGroupQuestion: IQuestionGroupQuestion): Observable<EntityResponseType> {
    return this.http.post<IQuestionGroupQuestion>(this.resourceUrl, questionGroupQuestion, { observe: 'response' });
  }

  update(questionGroupQuestion: IQuestionGroupQuestion): Observable<EntityResponseType> {
    return this.http.put<IQuestionGroupQuestion>(this.resourceUrl, questionGroupQuestion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQuestionGroupQuestion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQuestionGroupQuestion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

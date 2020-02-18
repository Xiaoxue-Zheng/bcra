import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IQuestionGroup } from 'app/shared/model/question-group.model';

type EntityResponseType = HttpResponse<IQuestionGroup>;
type EntityArrayResponseType = HttpResponse<IQuestionGroup[]>;

@Injectable({ providedIn: 'root' })
export class QuestionGroupService {
  public resourceUrl = SERVER_API_URL + 'api/question-groups';

  constructor(protected http: HttpClient) {}

  create(questionGroup: IQuestionGroup): Observable<EntityResponseType> {
    return this.http.post<IQuestionGroup>(this.resourceUrl, questionGroup, { observe: 'response' });
  }

  update(questionGroup: IQuestionGroup): Observable<EntityResponseType> {
    return this.http.put<IQuestionGroup>(this.resourceUrl, questionGroup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQuestionGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQuestionGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQuestionnaireQuestionGroup } from 'app/shared/model/questionnaire-question-group.model';

type EntityResponseType = HttpResponse<IQuestionnaireQuestionGroup>;
type EntityArrayResponseType = HttpResponse<IQuestionnaireQuestionGroup[]>;

@Injectable({ providedIn: 'root' })
export class QuestionnaireQuestionGroupService {
  public resourceUrl = SERVER_API_URL + 'api/questionnaire-question-groups';

  constructor(protected http: HttpClient) {}

  create(questionnaireQuestionGroup: IQuestionnaireQuestionGroup): Observable<EntityResponseType> {
    return this.http.post<IQuestionnaireQuestionGroup>(this.resourceUrl, questionnaireQuestionGroup, { observe: 'response' });
  }

  update(questionnaireQuestionGroup: IQuestionnaireQuestionGroup): Observable<EntityResponseType> {
    return this.http.put<IQuestionnaireQuestionGroup>(this.resourceUrl, questionnaireQuestionGroup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQuestionnaireQuestionGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQuestionnaireQuestionGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

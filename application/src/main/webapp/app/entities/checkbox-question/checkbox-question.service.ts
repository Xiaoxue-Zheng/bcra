import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICheckboxQuestion } from 'app/shared/model/checkbox-question.model';

type EntityResponseType = HttpResponse<ICheckboxQuestion>;
type EntityArrayResponseType = HttpResponse<ICheckboxQuestion[]>;

@Injectable({ providedIn: 'root' })
export class CheckboxQuestionService {
  public resourceUrl = SERVER_API_URL + 'api/checkbox-questions';

  constructor(protected http: HttpClient) {}

  create(checkboxQuestion: ICheckboxQuestion): Observable<EntityResponseType> {
    return this.http.post<ICheckboxQuestion>(this.resourceUrl, checkboxQuestion, { observe: 'response' });
  }

  update(checkboxQuestion: ICheckboxQuestion): Observable<EntityResponseType> {
    return this.http.put<ICheckboxQuestion>(this.resourceUrl, checkboxQuestion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICheckboxQuestion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICheckboxQuestion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

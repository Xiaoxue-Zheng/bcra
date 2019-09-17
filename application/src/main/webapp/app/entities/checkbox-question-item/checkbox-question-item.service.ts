import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICheckboxQuestionItem } from 'app/shared/model/checkbox-question-item.model';

type EntityResponseType = HttpResponse<ICheckboxQuestionItem>;
type EntityArrayResponseType = HttpResponse<ICheckboxQuestionItem[]>;

@Injectable({ providedIn: 'root' })
export class CheckboxQuestionItemService {
  public resourceUrl = SERVER_API_URL + 'api/checkbox-question-items';

  constructor(protected http: HttpClient) {}

  create(checkboxQuestionItem: ICheckboxQuestionItem): Observable<EntityResponseType> {
    return this.http.post<ICheckboxQuestionItem>(this.resourceUrl, checkboxQuestionItem, { observe: 'response' });
  }

  update(checkboxQuestionItem: ICheckboxQuestionItem): Observable<EntityResponseType> {
    return this.http.put<ICheckboxQuestionItem>(this.resourceUrl, checkboxQuestionItem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICheckboxQuestionItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICheckboxQuestionItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

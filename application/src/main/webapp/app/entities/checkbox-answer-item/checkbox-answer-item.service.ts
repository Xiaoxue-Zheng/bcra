import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICheckboxAnswerItem } from 'app/shared/model/checkbox-answer-item.model';

type EntityResponseType = HttpResponse<ICheckboxAnswerItem>;
type EntityArrayResponseType = HttpResponse<ICheckboxAnswerItem[]>;

@Injectable({ providedIn: 'root' })
export class CheckboxAnswerItemService {
  public resourceUrl = SERVER_API_URL + 'api/checkbox-answer-items';

  constructor(protected http: HttpClient) {}

  create(checkboxAnswerItem: ICheckboxAnswerItem): Observable<EntityResponseType> {
    return this.http.post<ICheckboxAnswerItem>(this.resourceUrl, checkboxAnswerItem, { observe: 'response' });
  }

  update(checkboxAnswerItem: ICheckboxAnswerItem): Observable<EntityResponseType> {
    return this.http.put<ICheckboxAnswerItem>(this.resourceUrl, checkboxAnswerItem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICheckboxAnswerItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICheckboxAnswerItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

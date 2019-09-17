import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRadioAnswerItem } from 'app/shared/model/radio-answer-item.model';

type EntityResponseType = HttpResponse<IRadioAnswerItem>;
type EntityArrayResponseType = HttpResponse<IRadioAnswerItem[]>;

@Injectable({ providedIn: 'root' })
export class RadioAnswerItemService {
  public resourceUrl = SERVER_API_URL + 'api/radio-answer-items';

  constructor(protected http: HttpClient) {}

  create(radioAnswerItem: IRadioAnswerItem): Observable<EntityResponseType> {
    return this.http.post<IRadioAnswerItem>(this.resourceUrl, radioAnswerItem, { observe: 'response' });
  }

  update(radioAnswerItem: IRadioAnswerItem): Observable<EntityResponseType> {
    return this.http.put<IRadioAnswerItem>(this.resourceUrl, radioAnswerItem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRadioAnswerItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRadioAnswerItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

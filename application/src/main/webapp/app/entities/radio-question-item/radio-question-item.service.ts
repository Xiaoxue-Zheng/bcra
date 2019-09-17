import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRadioQuestionItem } from 'app/shared/model/radio-question-item.model';

type EntityResponseType = HttpResponse<IRadioQuestionItem>;
type EntityArrayResponseType = HttpResponse<IRadioQuestionItem[]>;

@Injectable({ providedIn: 'root' })
export class RadioQuestionItemService {
  public resourceUrl = SERVER_API_URL + 'api/radio-question-items';

  constructor(protected http: HttpClient) {}

  create(radioQuestionItem: IRadioQuestionItem): Observable<EntityResponseType> {
    return this.http.post<IRadioQuestionItem>(this.resourceUrl, radioQuestionItem, { observe: 'response' });
  }

  update(radioQuestionItem: IRadioQuestionItem): Observable<EntityResponseType> {
    return this.http.put<IRadioQuestionItem>(this.resourceUrl, radioQuestionItem, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRadioQuestionItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRadioQuestionItem[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

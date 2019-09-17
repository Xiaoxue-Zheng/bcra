import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRadioAnswer } from 'app/shared/model/radio-answer.model';

type EntityResponseType = HttpResponse<IRadioAnswer>;
type EntityArrayResponseType = HttpResponse<IRadioAnswer[]>;

@Injectable({ providedIn: 'root' })
export class RadioAnswerService {
  public resourceUrl = SERVER_API_URL + 'api/radio-answers';

  constructor(protected http: HttpClient) {}

  create(radioAnswer: IRadioAnswer): Observable<EntityResponseType> {
    return this.http.post<IRadioAnswer>(this.resourceUrl, radioAnswer, { observe: 'response' });
  }

  update(radioAnswer: IRadioAnswer): Observable<EntityResponseType> {
    return this.http.put<IRadioAnswer>(this.resourceUrl, radioAnswer, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRadioAnswer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRadioAnswer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

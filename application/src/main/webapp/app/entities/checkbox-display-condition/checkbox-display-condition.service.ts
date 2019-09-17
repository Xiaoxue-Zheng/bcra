import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICheckboxDisplayCondition } from 'app/shared/model/checkbox-display-condition.model';

type EntityResponseType = HttpResponse<ICheckboxDisplayCondition>;
type EntityArrayResponseType = HttpResponse<ICheckboxDisplayCondition[]>;

@Injectable({ providedIn: 'root' })
export class CheckboxDisplayConditionService {
  public resourceUrl = SERVER_API_URL + 'api/checkbox-display-conditions';

  constructor(protected http: HttpClient) {}

  create(checkboxDisplayCondition: ICheckboxDisplayCondition): Observable<EntityResponseType> {
    return this.http.post<ICheckboxDisplayCondition>(this.resourceUrl, checkboxDisplayCondition, { observe: 'response' });
  }

  update(checkboxDisplayCondition: ICheckboxDisplayCondition): Observable<EntityResponseType> {
    return this.http.put<ICheckboxDisplayCondition>(this.resourceUrl, checkboxDisplayCondition, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICheckboxDisplayCondition>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICheckboxDisplayCondition[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

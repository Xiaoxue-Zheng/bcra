import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReferralCondition } from 'app/shared/model/referral-condition.model';

type EntityResponseType = HttpResponse<IReferralCondition>;
type EntityArrayResponseType = HttpResponse<IReferralCondition[]>;

@Injectable({ providedIn: 'root' })
export class ReferralConditionService {
  public resourceUrl = SERVER_API_URL + 'api/referral-conditions';

  constructor(protected http: HttpClient) {}

  create(referralCondition: IReferralCondition): Observable<EntityResponseType> {
    return this.http.post<IReferralCondition>(this.resourceUrl, referralCondition, { observe: 'response' });
  }

  update(referralCondition: IReferralCondition): Observable<EntityResponseType> {
    return this.http.put<IReferralCondition>(this.resourceUrl, referralCondition, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReferralCondition>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReferralCondition[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

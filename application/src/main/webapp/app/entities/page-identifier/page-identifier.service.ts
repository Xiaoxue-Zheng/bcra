import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPageIdentifier } from 'app/shared/model/page-identifier.model';

type EntityResponseType = HttpResponse<IPageIdentifier>;
type EntityArrayResponseType = HttpResponse<IPageIdentifier[]>;

@Injectable({ providedIn: 'root' })
export class PageIdentifierService {
  public resourceUrl = SERVER_API_URL + 'api/page-identifiers';

  constructor(protected http: HttpClient) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPageIdentifier>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPageIdentifier[]>(this.resourceUrl, { params: options, observe: 'response' });
  }
}

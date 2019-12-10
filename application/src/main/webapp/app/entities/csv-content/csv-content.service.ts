import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICsvContent } from 'app/shared/model/csv-content.model';

type EntityResponseType = HttpResponse<ICsvContent>;
type EntityArrayResponseType = HttpResponse<ICsvContent[]>;

@Injectable({ providedIn: 'root' })
export class CsvContentService {
  public resourceUrl = SERVER_API_URL + 'api/csv-contents';

  constructor(protected http: HttpClient) {}

  create(csvContent: ICsvContent): Observable<EntityResponseType> {
    return this.http.post<ICsvContent>(this.resourceUrl, csvContent, { observe: 'response' });
  }

  update(csvContent: ICsvContent): Observable<EntityResponseType> {
    return this.http.put<ICsvContent>(this.resourceUrl, csvContent, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICsvContent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICsvContent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IStudyId } from 'app/shared/model/study-id.model';

type EntityResponseType = HttpResponse<IStudyId>;
type EntityArrayResponseType = HttpResponse<IStudyId[]>;

@Injectable({ providedIn: 'root' })
export class StudyIdService {
  public resourceUrl = SERVER_API_URL + 'api/study-ids';

  constructor(protected http: HttpClient) {}

  create(studyId: IStudyId): Observable<EntityResponseType> {
    return this.http.post<IStudyId>(this.resourceUrl, studyId, { observe: 'response' });
  }

  update(studyId: IStudyId): Observable<EntityResponseType> {
    return this.http.put<IStudyId>(this.resourceUrl, studyId, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStudyId>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStudyId[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIdentifiableData } from 'app/shared/model/identifiable-data.model';

type EntityResponseType = HttpResponse<IIdentifiableData>;
type EntityArrayResponseType = HttpResponse<IIdentifiableData[]>;

@Injectable({ providedIn: 'root' })
export class IdentifiableDataService {
  public resourceUrl = SERVER_API_URL + 'api/identifiable-data';

  constructor(protected http: HttpClient) {}

  create(identifiableData: IIdentifiableData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(identifiableData);
    return this.http
      .post<IIdentifiableData>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(identifiableData: IIdentifiableData): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(identifiableData);
    return this.http
      .put<IIdentifiableData>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IIdentifiableData>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IIdentifiableData[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(identifiableData: IIdentifiableData): IIdentifiableData {
    const copy: IIdentifiableData = Object.assign({}, identifiableData, {
      dateOfBirth:
        identifiableData.dateOfBirth != null && identifiableData.dateOfBirth.isValid()
          ? identifiableData.dateOfBirth.format(DATE_FORMAT)
          : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOfBirth = res.body.dateOfBirth != null ? moment(res.body.dateOfBirth) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((identifiableData: IIdentifiableData) => {
        identifiableData.dateOfBirth = identifiableData.dateOfBirth != null ? moment(identifiableData.dateOfBirth) : null;
      });
    }
    return res;
  }
}

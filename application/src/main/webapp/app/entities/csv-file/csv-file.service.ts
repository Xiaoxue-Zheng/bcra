import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICsvFile } from 'app/shared/model/csv-file.model';

type EntityResponseType = HttpResponse<ICsvFile>;
type EntityArrayResponseType = HttpResponse<ICsvFile[]>;

@Injectable({ providedIn: 'root' })
export class CsvFileService {
  public resourceUrl = SERVER_API_URL + 'api/csv-files';

  constructor(protected http: HttpClient) {}

  create(csvFile: ICsvFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(csvFile);
    return this.http
      .post<ICsvFile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(csvFile: ICsvFile): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(csvFile);
    return this.http
      .put<ICsvFile>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICsvFile>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICsvFile[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(csvFile: ICsvFile): ICsvFile {
    const copy: ICsvFile = Object.assign({}, csvFile, {
      uploadDatetime: csvFile.uploadDatetime != null && csvFile.uploadDatetime.isValid() ? csvFile.uploadDatetime.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.uploadDatetime = res.body.uploadDatetime != null ? moment(res.body.uploadDatetime) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((csvFile: ICsvFile) => {
        csvFile.uploadDatetime = csvFile.uploadDatetime != null ? moment(csvFile.uploadDatetime) : null;
      });
    }
    return res;
  }
}

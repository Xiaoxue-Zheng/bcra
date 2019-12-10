/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { IdentifiableDataService } from 'app/entities/identifiable-data/identifiable-data.service';
import { IIdentifiableData, IdentifiableData } from 'app/shared/model/identifiable-data.model';

describe('Service Tests', () => {
  describe('IdentifiableData Service', () => {
    let injector: TestBed;
    let service: IdentifiableDataService;
    let httpMock: HttpTestingController;
    let elemDefault: IIdentifiableData;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(IdentifiableDataService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new IdentifiableData(
        0,
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dateOfBirth: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a IdentifiableData', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateOfBirth: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateOfBirth: currentDate
          },
          returnedFromService
        );
        service
          .create(new IdentifiableData(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a IdentifiableData', async () => {
        const returnedFromService = Object.assign(
          {
            nhsNumber: 'BBBBBB',
            dateOfBirth: currentDate.format(DATE_FORMAT),
            firstname: 'BBBBBB',
            surname: 'BBBBBB',
            email: 'BBBBBB',
            address1: 'BBBBBB',
            address2: 'BBBBBB',
            address3: 'BBBBBB',
            address4: 'BBBBBB',
            address5: 'BBBBBB',
            postcode: 'BBBBBB',
            practiceName: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfBirth: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of IdentifiableData', async () => {
        const returnedFromService = Object.assign(
          {
            nhsNumber: 'BBBBBB',
            dateOfBirth: currentDate.format(DATE_FORMAT),
            firstname: 'BBBBBB',
            surname: 'BBBBBB',
            email: 'BBBBBB',
            address1: 'BBBBBB',
            address2: 'BBBBBB',
            address3: 'BBBBBB',
            address4: 'BBBBBB',
            address5: 'BBBBBB',
            postcode: 'BBBBBB',
            practiceName: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateOfBirth: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a IdentifiableData', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

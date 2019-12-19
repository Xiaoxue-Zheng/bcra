/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ParticipantService } from 'app/entities/participant/participant.service';
import { IParticipant, Participant } from 'app/shared/model/participant.model';

describe('Service Tests', () => {
  describe('Participant Service', () => {
    let injector: TestBed;
    let service: ParticipantService;
    let httpMock: HttpTestingController;
    let elemDefault: IParticipant;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ParticipantService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Participant(0, currentDate, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            registerDatetime: currentDate.format(DATE_TIME_FORMAT),
            lastLoginDatetime: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a Participant', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            registerDatetime: currentDate.format(DATE_TIME_FORMAT),
            lastLoginDatetime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            registerDatetime: currentDate,
            lastLoginDatetime: currentDate
          },
          returnedFromService
        );
        service
          .create(new Participant(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Participant', async () => {
        const returnedFromService = Object.assign(
          {
            registerDatetime: currentDate.format(DATE_TIME_FORMAT),
            lastLoginDatetime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            registerDatetime: currentDate,
            lastLoginDatetime: currentDate
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

      it('should return a list of Participant', async () => {
        const returnedFromService = Object.assign(
          {
            registerDatetime: currentDate.format(DATE_TIME_FORMAT),
            lastLoginDatetime: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            registerDatetime: currentDate,
            lastLoginDatetime: currentDate
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

      it('should delete a Participant', async () => {
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

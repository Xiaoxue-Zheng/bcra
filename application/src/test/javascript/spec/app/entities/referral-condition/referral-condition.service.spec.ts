import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ReferralConditionService } from 'app/entities/referral-condition/referral-condition.service';
import { IReferralCondition, ReferralCondition } from 'app/shared/model/referral-condition.model';
import { ReferralConditionType } from 'app/shared/model/enumerations/referral-condition-type.model';
import { QuestionIdentifier } from 'app/shared/model/enumerations/question-identifier.model';
import { QuestionItemIdentifier } from 'app/shared/model/enumerations/question-item-identifier.model';

describe('Service Tests', () => {
  describe('ReferralCondition Service', () => {
    let injector: TestBed;
    let service: ReferralConditionService;
    let httpMock: HttpTestingController;
    let elemDefault: IReferralCondition;
    let expectedResult: IReferralCondition | IReferralCondition[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ReferralConditionService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ReferralCondition(
        0,
        0,
        ReferralConditionType.ITEMS_AT_LEAST,
        QuestionIdentifier.CONSENT_INFO_SHEET,
        QuestionItemIdentifier.CONSENT_INFO_SHEET_YES,
        0,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ReferralCondition', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ReferralCondition()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ReferralCondition', () => {
        const returnedFromService = Object.assign(
          {
            andGroup: 1,
            type: 'BBBBBB',
            questionIdentifier: 'BBBBBB',
            itemIdentifier: 'BBBBBB',
            number: 1,
            reason: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ReferralCondition', () => {
        const returnedFromService = Object.assign(
          {
            andGroup: 1,
            type: 'BBBBBB',
            questionIdentifier: 'BBBBBB',
            itemIdentifier: 'BBBBBB',
            number: 1,
            reason: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ReferralCondition', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

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

import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { QuestionGroupService } from 'app/entities/question-group/question-group.service';
import { IQuestionGroup, QuestionGroup } from 'app/shared/model/question-group.model';
import { QuestionGroupIdentifier } from 'app/shared/model/enumerations/question-group-identifier.model';

describe('Service Tests', () => {
  describe('QuestionGroup Service', () => {
    let injector: TestBed;
    let service: QuestionGroupService;
    let httpMock: HttpTestingController;
    let elemDefault: IQuestionGroup;
    let expectedResult: IQuestionGroup | IQuestionGroup[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(QuestionGroupService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new QuestionGroup(0, QuestionGroupIdentifier.CONSENT_FORM_QUESTIONS);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a QuestionGroup', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new QuestionGroup()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a QuestionGroup', () => {
        const returnedFromService = Object.assign(
          {
            identifier: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of QuestionGroup', () => {
        const returnedFromService = Object.assign(
          {
            identifier: 'BBBBBB'
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

      it('should delete a QuestionGroup', () => {
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

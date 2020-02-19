import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { QuestionSectionService } from 'app/entities/question-section/question-section.service';
import { IQuestionSection, QuestionSection } from 'app/shared/model/question-section.model';
import { QuestionSectionIdentifier } from 'app/shared/model/enumerations/question-section-identifier.model';

describe('Service Tests', () => {
  describe('QuestionSection Service', () => {
    let injector: TestBed;
    let service: QuestionSectionService;
    let httpMock: HttpTestingController;
    let elemDefault: IQuestionSection;
    let expectedResult: IQuestionSection | IQuestionSection[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(QuestionSectionService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new QuestionSection(0, QuestionSectionIdentifier.CONSENT_FORM, 'AAAAAAA', 0, 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a QuestionSection', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new QuestionSection()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a QuestionSection', () => {
        const returnedFromService = Object.assign(
          {
            identifier: 'BBBBBB',
            title: 'BBBBBB',
            order: 1,
            url: 'BBBBBB',
            progress: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of QuestionSection', () => {
        const returnedFromService = Object.assign(
          {
            identifier: 'BBBBBB',
            title: 'BBBBBB',
            order: 1,
            url: 'BBBBBB',
            progress: 1
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

      it('should delete a QuestionSection', () => {
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

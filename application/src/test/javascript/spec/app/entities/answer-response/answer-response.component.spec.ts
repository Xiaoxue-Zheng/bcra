/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BcraTestModule } from '../../../test.module';
import { AnswerResponseComponent } from 'app/entities/answer-response/answer-response.component';
import { AnswerResponseService } from 'app/entities/answer-response/answer-response.service';
import { AnswerResponse } from 'app/shared/model/answer-response.model';

describe('Component Tests', () => {
  describe('AnswerResponse Management Component', () => {
    let comp: AnswerResponseComponent;
    let fixture: ComponentFixture<AnswerResponseComponent>;
    let service: AnswerResponseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [AnswerResponseComponent],
        providers: []
      })
        .overrideTemplate(AnswerResponseComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnswerResponseComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnswerResponseService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AnswerResponse(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.answerResponses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BcraTestModule } from '../../../test.module';
import { AnswerSectionComponent } from 'app/entities/answer-section/answer-section.component';
import { AnswerSectionService } from 'app/entities/answer-section/answer-section.service';
import { AnswerSection } from 'app/shared/model/answer-section.model';

describe('Component Tests', () => {
  describe('AnswerSection Management Component', () => {
    let comp: AnswerSectionComponent;
    let fixture: ComponentFixture<AnswerSectionComponent>;
    let service: AnswerSectionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [AnswerSectionComponent],
        providers: []
      })
        .overrideTemplate(AnswerSectionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnswerSectionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnswerSectionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AnswerSection(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.answerSections[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

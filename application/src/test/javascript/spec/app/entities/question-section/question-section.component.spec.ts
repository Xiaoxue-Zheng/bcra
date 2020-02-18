import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BcraTestModule } from '../../../test.module';
import { QuestionSectionComponent } from 'app/entities/question-section/question-section.component';
import { QuestionSectionService } from 'app/entities/question-section/question-section.service';
import { QuestionSection } from 'app/shared/model/question-section.model';

describe('Component Tests', () => {
  describe('QuestionSection Management Component', () => {
    let comp: QuestionSectionComponent;
    let fixture: ComponentFixture<QuestionSectionComponent>;
    let service: QuestionSectionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [QuestionSectionComponent]
      })
        .overrideTemplate(QuestionSectionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuestionSectionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionSectionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new QuestionSection(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.questionSections && comp.questionSections[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

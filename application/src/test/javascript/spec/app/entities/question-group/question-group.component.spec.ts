import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BcraTestModule } from '../../../test.module';
import { QuestionGroupComponent } from 'app/entities/question-group/question-group.component';
import { QuestionGroupService } from 'app/entities/question-group/question-group.service';
import { QuestionGroup } from 'app/shared/model/question-group.model';

describe('Component Tests', () => {
  describe('QuestionGroup Management Component', () => {
    let comp: QuestionGroupComponent;
    let fixture: ComponentFixture<QuestionGroupComponent>;
    let service: QuestionGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [QuestionGroupComponent]
      })
        .overrideTemplate(QuestionGroupComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuestionGroupComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionGroupService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new QuestionGroup(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.questionGroups && comp.questionGroups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

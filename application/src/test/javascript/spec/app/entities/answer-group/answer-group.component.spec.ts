/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BcraTestModule } from '../../../test.module';
import { AnswerGroupComponent } from 'app/entities/answer-group/answer-group.component';
import { AnswerGroupService } from 'app/entities/answer-group/answer-group.service';
import { AnswerGroup } from 'app/shared/model/answer-group.model';

describe('Component Tests', () => {
  describe('AnswerGroup Management Component', () => {
    let comp: AnswerGroupComponent;
    let fixture: ComponentFixture<AnswerGroupComponent>;
    let service: AnswerGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [AnswerGroupComponent],
        providers: []
      })
        .overrideTemplate(AnswerGroupComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnswerGroupComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnswerGroupService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AnswerGroup(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.answerGroups[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BcraTestModule } from '../../../test.module';
import { AnswerItemComponent } from 'app/entities/answer-item/answer-item.component';
import { AnswerItemService } from 'app/entities/answer-item/answer-item.service';
import { AnswerItem } from 'app/shared/model/answer-item.model';

describe('Component Tests', () => {
  describe('AnswerItem Management Component', () => {
    let comp: AnswerItemComponent;
    let fixture: ComponentFixture<AnswerItemComponent>;
    let service: AnswerItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [AnswerItemComponent],
        providers: []
      })
        .overrideTemplate(AnswerItemComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnswerItemComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnswerItemService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AnswerItem(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.answerItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

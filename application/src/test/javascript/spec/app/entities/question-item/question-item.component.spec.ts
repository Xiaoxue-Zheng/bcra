/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BcraTestModule } from '../../../test.module';
import { QuestionItemComponent } from 'app/entities/question-item/question-item.component';
import { QuestionItemService } from 'app/entities/question-item/question-item.service';
import { QuestionItem } from 'app/shared/model/question-item.model';

describe('Component Tests', () => {
  describe('QuestionItem Management Component', () => {
    let comp: QuestionItemComponent;
    let fixture: ComponentFixture<QuestionItemComponent>;
    let service: QuestionItemService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [QuestionItemComponent],
        providers: []
      })
        .overrideTemplate(QuestionItemComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuestionItemComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionItemService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new QuestionItem(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.questionItems[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

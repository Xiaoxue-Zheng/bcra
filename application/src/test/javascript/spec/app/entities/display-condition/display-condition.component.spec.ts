/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BcraTestModule } from '../../../test.module';
import { DisplayConditionComponent } from 'app/entities/display-condition/display-condition.component';
import { DisplayConditionService } from 'app/entities/display-condition/display-condition.service';
import { DisplayCondition } from 'app/shared/model/display-condition.model';

describe('Component Tests', () => {
  describe('DisplayCondition Management Component', () => {
    let comp: DisplayConditionComponent;
    let fixture: ComponentFixture<DisplayConditionComponent>;
    let service: DisplayConditionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [DisplayConditionComponent],
        providers: []
      })
        .overrideTemplate(DisplayConditionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DisplayConditionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DisplayConditionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DisplayCondition(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.displayConditions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

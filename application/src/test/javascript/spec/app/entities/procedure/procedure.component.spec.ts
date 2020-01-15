/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BcraTestModule } from '../../../test.module';
import { ProcedureComponent } from 'app/entities/procedure/procedure.component';
import { ProcedureService } from 'app/entities/procedure/procedure.service';
import { Procedure } from 'app/shared/model/procedure.model';

describe('Component Tests', () => {
  describe('Procedure Management Component', () => {
    let comp: ProcedureComponent;
    let fixture: ComponentFixture<ProcedureComponent>;
    let service: ProcedureService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [ProcedureComponent],
        providers: []
      })
        .overrideTemplate(ProcedureComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProcedureComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProcedureService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Procedure(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.procedures[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

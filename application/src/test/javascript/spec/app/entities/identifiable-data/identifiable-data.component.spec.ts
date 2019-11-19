/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BcraTestModule } from '../../../test.module';
import { IdentifiableDataComponent } from 'app/entities/identifiable-data/identifiable-data.component';
import { IdentifiableDataService } from 'app/entities/identifiable-data/identifiable-data.service';
import { IdentifiableData } from 'app/shared/model/identifiable-data.model';

describe('Component Tests', () => {
  describe('IdentifiableData Management Component', () => {
    let comp: IdentifiableDataComponent;
    let fixture: ComponentFixture<IdentifiableDataComponent>;
    let service: IdentifiableDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [IdentifiableDataComponent],
        providers: []
      })
        .overrideTemplate(IdentifiableDataComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IdentifiableDataComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IdentifiableDataService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new IdentifiableData(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.identifiableData[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

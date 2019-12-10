/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BcraTestModule } from '../../../test.module';
import { CsvContentComponent } from 'app/entities/csv-content/csv-content.component';
import { CsvContentService } from 'app/entities/csv-content/csv-content.service';
import { CsvContent } from 'app/shared/model/csv-content.model';

describe('Component Tests', () => {
  describe('CsvContent Management Component', () => {
    let comp: CsvContentComponent;
    let fixture: ComponentFixture<CsvContentComponent>;
    let service: CsvContentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [CsvContentComponent],
        providers: []
      })
        .overrideTemplate(CsvContentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CsvContentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CsvContentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CsvContent(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.csvContents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

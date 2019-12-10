/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { CsvContentUpdateComponent } from 'app/entities/csv-content/csv-content-update.component';
import { CsvContentService } from 'app/entities/csv-content/csv-content.service';
import { CsvContent } from 'app/shared/model/csv-content.model';

describe('Component Tests', () => {
  describe('CsvContent Management Update Component', () => {
    let comp: CsvContentUpdateComponent;
    let fixture: ComponentFixture<CsvContentUpdateComponent>;
    let service: CsvContentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [CsvContentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CsvContentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CsvContentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CsvContentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CsvContent(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CsvContent();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});

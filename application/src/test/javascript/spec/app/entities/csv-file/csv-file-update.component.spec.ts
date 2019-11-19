/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { CsvFileUpdateComponent } from 'app/entities/csv-file/csv-file-update.component';
import { CsvFileService } from 'app/entities/csv-file/csv-file.service';
import { CsvFile } from 'app/shared/model/csv-file.model';

describe('Component Tests', () => {
  describe('CsvFile Management Update Component', () => {
    let comp: CsvFileUpdateComponent;
    let fixture: ComponentFixture<CsvFileUpdateComponent>;
    let service: CsvFileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [CsvFileUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CsvFileUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CsvFileUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CsvFileService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CsvFile(123);
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
        const entity = new CsvFile();
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

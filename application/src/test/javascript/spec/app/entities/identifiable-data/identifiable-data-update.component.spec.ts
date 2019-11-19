/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { IdentifiableDataUpdateComponent } from 'app/entities/identifiable-data/identifiable-data-update.component';
import { IdentifiableDataService } from 'app/entities/identifiable-data/identifiable-data.service';
import { IdentifiableData } from 'app/shared/model/identifiable-data.model';

describe('Component Tests', () => {
  describe('IdentifiableData Management Update Component', () => {
    let comp: IdentifiableDataUpdateComponent;
    let fixture: ComponentFixture<IdentifiableDataUpdateComponent>;
    let service: IdentifiableDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [IdentifiableDataUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(IdentifiableDataUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IdentifiableDataUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IdentifiableDataService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new IdentifiableData(123);
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
        const entity = new IdentifiableData();
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

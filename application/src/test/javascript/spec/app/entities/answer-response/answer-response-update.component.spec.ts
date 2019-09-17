/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { AnswerResponseUpdateComponent } from 'app/entities/answer-response/answer-response-update.component';
import { AnswerResponseService } from 'app/entities/answer-response/answer-response.service';
import { AnswerResponse } from 'app/shared/model/answer-response.model';

describe('Component Tests', () => {
  describe('AnswerResponse Management Update Component', () => {
    let comp: AnswerResponseUpdateComponent;
    let fixture: ComponentFixture<AnswerResponseUpdateComponent>;
    let service: AnswerResponseService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [AnswerResponseUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AnswerResponseUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnswerResponseUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnswerResponseService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnswerResponse(123);
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
        const entity = new AnswerResponse();
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

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BcraTestModule } from '../../../test.module';
import { AnswerGroupUpdateComponent } from 'app/entities/answer-group/answer-group-update.component';
import { AnswerGroupService } from 'app/entities/answer-group/answer-group.service';
import { AnswerGroup } from 'app/shared/model/answer-group.model';

describe('Component Tests', () => {
  describe('AnswerGroup Management Update Component', () => {
    let comp: AnswerGroupUpdateComponent;
    let fixture: ComponentFixture<AnswerGroupUpdateComponent>;
    let service: AnswerGroupService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [AnswerGroupUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AnswerGroupUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnswerGroupUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnswerGroupService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnswerGroup(123);
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
        const entity = new AnswerGroup();
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

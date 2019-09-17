/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BcraTestModule } from '../../../test.module';
import { CheckboxQuestionDeleteDialogComponent } from 'app/entities/checkbox-question/checkbox-question-delete-dialog.component';
import { CheckboxQuestionService } from 'app/entities/checkbox-question/checkbox-question.service';

describe('Component Tests', () => {
  describe('CheckboxQuestion Management Delete Component', () => {
    let comp: CheckboxQuestionDeleteDialogComponent;
    let fixture: ComponentFixture<CheckboxQuestionDeleteDialogComponent>;
    let service: CheckboxQuestionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [CheckboxQuestionDeleteDialogComponent]
      })
        .overrideTemplate(CheckboxQuestionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CheckboxQuestionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CheckboxQuestionService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});

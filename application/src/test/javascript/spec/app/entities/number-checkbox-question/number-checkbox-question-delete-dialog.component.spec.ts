/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BcraTestModule } from '../../../test.module';
import { NumberCheckboxQuestionDeleteDialogComponent } from 'app/entities/number-checkbox-question/number-checkbox-question-delete-dialog.component';
import { NumberCheckboxQuestionService } from 'app/entities/number-checkbox-question/number-checkbox-question.service';

describe('Component Tests', () => {
  describe('NumberCheckboxQuestion Management Delete Component', () => {
    let comp: NumberCheckboxQuestionDeleteDialogComponent;
    let fixture: ComponentFixture<NumberCheckboxQuestionDeleteDialogComponent>;
    let service: NumberCheckboxQuestionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [NumberCheckboxQuestionDeleteDialogComponent]
      })
        .overrideTemplate(NumberCheckboxQuestionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NumberCheckboxQuestionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NumberCheckboxQuestionService);
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

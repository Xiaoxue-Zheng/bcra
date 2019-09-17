/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BcraTestModule } from '../../../test.module';
import { NumberCheckboxAnswerDeleteDialogComponent } from 'app/entities/number-checkbox-answer/number-checkbox-answer-delete-dialog.component';
import { NumberCheckboxAnswerService } from 'app/entities/number-checkbox-answer/number-checkbox-answer.service';

describe('Component Tests', () => {
  describe('NumberCheckboxAnswer Management Delete Component', () => {
    let comp: NumberCheckboxAnswerDeleteDialogComponent;
    let fixture: ComponentFixture<NumberCheckboxAnswerDeleteDialogComponent>;
    let service: NumberCheckboxAnswerService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [NumberCheckboxAnswerDeleteDialogComponent]
      })
        .overrideTemplate(NumberCheckboxAnswerDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NumberCheckboxAnswerDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NumberCheckboxAnswerService);
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

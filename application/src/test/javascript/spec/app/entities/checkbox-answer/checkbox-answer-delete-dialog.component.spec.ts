/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BcraTestModule } from '../../../test.module';
import { CheckboxAnswerDeleteDialogComponent } from 'app/entities/checkbox-answer/checkbox-answer-delete-dialog.component';
import { CheckboxAnswerService } from 'app/entities/checkbox-answer/checkbox-answer.service';

describe('Component Tests', () => {
  describe('CheckboxAnswer Management Delete Component', () => {
    let comp: CheckboxAnswerDeleteDialogComponent;
    let fixture: ComponentFixture<CheckboxAnswerDeleteDialogComponent>;
    let service: CheckboxAnswerService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [CheckboxAnswerDeleteDialogComponent]
      })
        .overrideTemplate(CheckboxAnswerDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CheckboxAnswerDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CheckboxAnswerService);
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

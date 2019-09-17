/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BcraTestModule } from '../../../test.module';
import { RadioQuestionDeleteDialogComponent } from 'app/entities/radio-question/radio-question-delete-dialog.component';
import { RadioQuestionService } from 'app/entities/radio-question/radio-question.service';

describe('Component Tests', () => {
  describe('RadioQuestion Management Delete Component', () => {
    let comp: RadioQuestionDeleteDialogComponent;
    let fixture: ComponentFixture<RadioQuestionDeleteDialogComponent>;
    let service: RadioQuestionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RadioQuestionDeleteDialogComponent]
      })
        .overrideTemplate(RadioQuestionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RadioQuestionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RadioQuestionService);
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

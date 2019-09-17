/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BcraTestModule } from '../../../test.module';
import { RepeatQuestionDeleteDialogComponent } from 'app/entities/repeat-question/repeat-question-delete-dialog.component';
import { RepeatQuestionService } from 'app/entities/repeat-question/repeat-question.service';

describe('Component Tests', () => {
  describe('RepeatQuestion Management Delete Component', () => {
    let comp: RepeatQuestionDeleteDialogComponent;
    let fixture: ComponentFixture<RepeatQuestionDeleteDialogComponent>;
    let service: RepeatQuestionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [RepeatQuestionDeleteDialogComponent]
      })
        .overrideTemplate(RepeatQuestionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RepeatQuestionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RepeatQuestionService);
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

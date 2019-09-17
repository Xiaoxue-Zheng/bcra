/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BcraTestModule } from '../../../test.module';
import { AnswerResponseDeleteDialogComponent } from 'app/entities/answer-response/answer-response-delete-dialog.component';
import { AnswerResponseService } from 'app/entities/answer-response/answer-response.service';

describe('Component Tests', () => {
  describe('AnswerResponse Management Delete Component', () => {
    let comp: AnswerResponseDeleteDialogComponent;
    let fixture: ComponentFixture<AnswerResponseDeleteDialogComponent>;
    let service: AnswerResponseService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [AnswerResponseDeleteDialogComponent]
      })
        .overrideTemplate(AnswerResponseDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnswerResponseDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnswerResponseService);
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

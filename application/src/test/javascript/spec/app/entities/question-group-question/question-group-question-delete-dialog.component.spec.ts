/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BcraTestModule } from '../../../test.module';
import { QuestionGroupQuestionDeleteDialogComponent } from 'app/entities/question-group-question/question-group-question-delete-dialog.component';
import { QuestionGroupQuestionService } from 'app/entities/question-group-question/question-group-question.service';

describe('Component Tests', () => {
  describe('QuestionGroupQuestion Management Delete Component', () => {
    let comp: QuestionGroupQuestionDeleteDialogComponent;
    let fixture: ComponentFixture<QuestionGroupQuestionDeleteDialogComponent>;
    let service: QuestionGroupQuestionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [QuestionGroupQuestionDeleteDialogComponent]
      })
        .overrideTemplate(QuestionGroupQuestionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QuestionGroupQuestionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionGroupQuestionService);
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

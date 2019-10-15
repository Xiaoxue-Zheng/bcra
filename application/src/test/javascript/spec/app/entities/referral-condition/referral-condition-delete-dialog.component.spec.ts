/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BcraTestModule } from '../../../test.module';
import { ReferralConditionDeleteDialogComponent } from 'app/entities/referral-condition/referral-condition-delete-dialog.component';
import { ReferralConditionService } from 'app/entities/referral-condition/referral-condition.service';

describe('Component Tests', () => {
  describe('ReferralCondition Management Delete Component', () => {
    let comp: ReferralConditionDeleteDialogComponent;
    let fixture: ComponentFixture<ReferralConditionDeleteDialogComponent>;
    let service: ReferralConditionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BcraTestModule],
        declarations: [ReferralConditionDeleteDialogComponent]
      })
        .overrideTemplate(ReferralConditionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReferralConditionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReferralConditionService);
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

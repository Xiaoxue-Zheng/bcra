import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BcraSharedModule } from 'app/shared';
import { participantRoute } from './participant.route';
import { ParticipantComponent } from './participant.component';

import { DefaultValuePipe } from 'app/shared/util/default-pipe';

@NgModule({
  imports: [BcraSharedModule, RouterModule.forChild([participantRoute])],
  declarations: [ParticipantComponent, DefaultValuePipe],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BcraParticipantModule {}

/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ParticipantComponentsPage, ParticipantDeleteDialog, ParticipantUpdatePage } from './participant.page-object';

const expect = chai.expect;

describe('Participant e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let participantUpdatePage: ParticipantUpdatePage;
  let participantComponentsPage: ParticipantComponentsPage;
  /*let participantDeleteDialog: ParticipantDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Participants', async () => {
    await navBarPage.goToEntity('participant');
    participantComponentsPage = new ParticipantComponentsPage();
    await browser.wait(ec.visibilityOf(participantComponentsPage.title), 5000);
    expect(await participantComponentsPage.getTitle()).to.eq('Participants');
  });

  it('should load create Participant page', async () => {
    await participantComponentsPage.clickOnCreateButton();
    participantUpdatePage = new ParticipantUpdatePage();
    expect(await participantUpdatePage.getPageTitle()).to.eq('Create or edit a Participant');
    await participantUpdatePage.cancel();
  });

  /* it('should create and save Participants', async () => {
        const nbButtonsBeforeCreate = await participantComponentsPage.countDeleteButtons();

        await participantComponentsPage.clickOnCreateButton();
        await promise.all([
            participantUpdatePage.setRegisterDatetimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            participantUpdatePage.setLastLoginDatetimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            participantUpdatePage.userSelectLastOption(),
        ]);
        expect(await participantUpdatePage.getRegisterDatetimeInput()).to.contain('2001-01-01T02:30', 'Expected registerDatetime value to be equals to 2000-12-31');
        expect(await participantUpdatePage.getLastLoginDatetimeInput()).to.contain('2001-01-01T02:30', 'Expected lastLoginDatetime value to be equals to 2000-12-31');
        await participantUpdatePage.save();
        expect(await participantUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await participantComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last Participant', async () => {
        const nbButtonsBeforeDelete = await participantComponentsPage.countDeleteButtons();
        await participantComponentsPage.clickOnLastDeleteButton();

        participantDeleteDialog = new ParticipantDeleteDialog();
        expect(await participantDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Participant?');
        await participantDeleteDialog.clickOnConfirmButton();

        expect(await participantComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

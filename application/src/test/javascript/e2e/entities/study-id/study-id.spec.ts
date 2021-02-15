import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { StudyIdComponentsPage, StudyIdDeleteDialog, StudyIdUpdatePage } from './study-id.page-object';

const expect = chai.expect;

describe('StudyId e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let studyIdComponentsPage: StudyIdComponentsPage;
  let studyIdUpdatePage: StudyIdUpdatePage;
  let studyIdDeleteDialog: StudyIdDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load StudyIds', async () => {
    await navBarPage.goToEntity('study-id');
    studyIdComponentsPage = new StudyIdComponentsPage();
    await browser.wait(ec.visibilityOf(studyIdComponentsPage.title), 5000);
    expect(await studyIdComponentsPage.getTitle()).to.eq('Study Ids');
    await browser.wait(ec.or(ec.visibilityOf(studyIdComponentsPage.entities), ec.visibilityOf(studyIdComponentsPage.noResult)), 1000);
  });

  it('should load create StudyId page', async () => {
    await studyIdComponentsPage.clickOnCreateButton();
    studyIdUpdatePage = new StudyIdUpdatePage();
    expect(await studyIdUpdatePage.getPageTitle()).to.eq('Create or edit a Study Id');
    await studyIdUpdatePage.cancel();
  });

  it('should create and save StudyIds', async () => {
    const nbButtonsBeforeCreate = await studyIdComponentsPage.countDeleteButtons();

    await studyIdComponentsPage.clickOnCreateButton();

    await promise.all([studyIdUpdatePage.setCodeInput('code'), studyIdUpdatePage.participantSelectLastOption()]);

    expect(await studyIdUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');

    await studyIdUpdatePage.save();
    expect(await studyIdUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await studyIdComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last StudyId', async () => {
    const nbButtonsBeforeDelete = await studyIdComponentsPage.countDeleteButtons();
    await studyIdComponentsPage.clickOnLastDeleteButton();

    studyIdDeleteDialog = new StudyIdDeleteDialog();
    expect(await studyIdDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Study Id?');
    await studyIdDeleteDialog.clickOnConfirmButton();

    expect(await studyIdComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

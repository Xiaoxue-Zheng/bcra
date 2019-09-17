/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RadioQuestionComponentsPage, RadioQuestionDeleteDialog, RadioQuestionUpdatePage } from './radio-question.page-object';

const expect = chai.expect;

describe('RadioQuestion e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let radioQuestionUpdatePage: RadioQuestionUpdatePage;
  let radioQuestionComponentsPage: RadioQuestionComponentsPage;
  let radioQuestionDeleteDialog: RadioQuestionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RadioQuestions', async () => {
    await navBarPage.goToEntity('radio-question');
    radioQuestionComponentsPage = new RadioQuestionComponentsPage();
    await browser.wait(ec.visibilityOf(radioQuestionComponentsPage.title), 5000);
    expect(await radioQuestionComponentsPage.getTitle()).to.eq('Radio Questions');
  });

  it('should load create RadioQuestion page', async () => {
    await radioQuestionComponentsPage.clickOnCreateButton();
    radioQuestionUpdatePage = new RadioQuestionUpdatePage();
    expect(await radioQuestionUpdatePage.getPageTitle()).to.eq('Create or edit a Radio Question');
    await radioQuestionUpdatePage.cancel();
  });

  it('should create and save RadioQuestions', async () => {
    const nbButtonsBeforeCreate = await radioQuestionComponentsPage.countDeleteButtons();

    await radioQuestionComponentsPage.clickOnCreateButton();
    await promise.all([]);
    await radioQuestionUpdatePage.save();
    expect(await radioQuestionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await radioQuestionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last RadioQuestion', async () => {
    const nbButtonsBeforeDelete = await radioQuestionComponentsPage.countDeleteButtons();
    await radioQuestionComponentsPage.clickOnLastDeleteButton();

    radioQuestionDeleteDialog = new RadioQuestionDeleteDialog();
    expect(await radioQuestionDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Radio Question?');
    await radioQuestionDeleteDialog.clickOnConfirmButton();

    expect(await radioQuestionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { QuestionGroupComponentsPage, QuestionGroupDeleteDialog, QuestionGroupUpdatePage } from './question-group.page-object';

const expect = chai.expect;

describe('QuestionGroup e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let questionGroupUpdatePage: QuestionGroupUpdatePage;
  let questionGroupComponentsPage: QuestionGroupComponentsPage;
  let questionGroupDeleteDialog: QuestionGroupDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load QuestionGroups', async () => {
    await navBarPage.goToEntity('question-group');
    questionGroupComponentsPage = new QuestionGroupComponentsPage();
    await browser.wait(ec.visibilityOf(questionGroupComponentsPage.title), 5000);
    expect(await questionGroupComponentsPage.getTitle()).to.eq('Question Groups');
  });

  it('should load create QuestionGroup page', async () => {
    await questionGroupComponentsPage.clickOnCreateButton();
    questionGroupUpdatePage = new QuestionGroupUpdatePage();
    expect(await questionGroupUpdatePage.getPageTitle()).to.eq('Create or edit a Question Group');
    await questionGroupUpdatePage.cancel();
  });

  it('should create and save QuestionGroups', async () => {
    const nbButtonsBeforeCreate = await questionGroupComponentsPage.countDeleteButtons();

    await questionGroupComponentsPage.clickOnCreateButton();
    await promise.all([questionGroupUpdatePage.setUuidInput('uuid')]);
    expect(await questionGroupUpdatePage.getUuidInput()).to.eq('uuid', 'Expected Uuid value to be equals to uuid');
    await questionGroupUpdatePage.save();
    expect(await questionGroupUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await questionGroupComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last QuestionGroup', async () => {
    const nbButtonsBeforeDelete = await questionGroupComponentsPage.countDeleteButtons();
    await questionGroupComponentsPage.clickOnLastDeleteButton();

    questionGroupDeleteDialog = new QuestionGroupDeleteDialog();
    expect(await questionGroupDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Question Group?');
    await questionGroupDeleteDialog.clickOnConfirmButton();

    expect(await questionGroupComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

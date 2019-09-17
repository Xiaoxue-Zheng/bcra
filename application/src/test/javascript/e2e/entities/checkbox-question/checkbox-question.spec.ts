/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CheckboxQuestionComponentsPage, CheckboxQuestionDeleteDialog, CheckboxQuestionUpdatePage } from './checkbox-question.page-object';

const expect = chai.expect;

describe('CheckboxQuestion e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let checkboxQuestionUpdatePage: CheckboxQuestionUpdatePage;
  let checkboxQuestionComponentsPage: CheckboxQuestionComponentsPage;
  let checkboxQuestionDeleteDialog: CheckboxQuestionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CheckboxQuestions', async () => {
    await navBarPage.goToEntity('checkbox-question');
    checkboxQuestionComponentsPage = new CheckboxQuestionComponentsPage();
    await browser.wait(ec.visibilityOf(checkboxQuestionComponentsPage.title), 5000);
    expect(await checkboxQuestionComponentsPage.getTitle()).to.eq('Checkbox Questions');
  });

  it('should load create CheckboxQuestion page', async () => {
    await checkboxQuestionComponentsPage.clickOnCreateButton();
    checkboxQuestionUpdatePage = new CheckboxQuestionUpdatePage();
    expect(await checkboxQuestionUpdatePage.getPageTitle()).to.eq('Create or edit a Checkbox Question');
    await checkboxQuestionUpdatePage.cancel();
  });

  it('should create and save CheckboxQuestions', async () => {
    const nbButtonsBeforeCreate = await checkboxQuestionComponentsPage.countDeleteButtons();

    await checkboxQuestionComponentsPage.clickOnCreateButton();
    await promise.all([]);
    await checkboxQuestionUpdatePage.save();
    expect(await checkboxQuestionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await checkboxQuestionComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CheckboxQuestion', async () => {
    const nbButtonsBeforeDelete = await checkboxQuestionComponentsPage.countDeleteButtons();
    await checkboxQuestionComponentsPage.clickOnLastDeleteButton();

    checkboxQuestionDeleteDialog = new CheckboxQuestionDeleteDialog();
    expect(await checkboxQuestionDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Checkbox Question?');
    await checkboxQuestionDeleteDialog.clickOnConfirmButton();

    expect(await checkboxQuestionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

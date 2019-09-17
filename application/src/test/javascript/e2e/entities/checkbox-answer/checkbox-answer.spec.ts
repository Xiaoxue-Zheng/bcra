/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CheckboxAnswerComponentsPage, CheckboxAnswerDeleteDialog, CheckboxAnswerUpdatePage } from './checkbox-answer.page-object';

const expect = chai.expect;

describe('CheckboxAnswer e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let checkboxAnswerUpdatePage: CheckboxAnswerUpdatePage;
  let checkboxAnswerComponentsPage: CheckboxAnswerComponentsPage;
  let checkboxAnswerDeleteDialog: CheckboxAnswerDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CheckboxAnswers', async () => {
    await navBarPage.goToEntity('checkbox-answer');
    checkboxAnswerComponentsPage = new CheckboxAnswerComponentsPage();
    await browser.wait(ec.visibilityOf(checkboxAnswerComponentsPage.title), 5000);
    expect(await checkboxAnswerComponentsPage.getTitle()).to.eq('Checkbox Answers');
  });

  it('should load create CheckboxAnswer page', async () => {
    await checkboxAnswerComponentsPage.clickOnCreateButton();
    checkboxAnswerUpdatePage = new CheckboxAnswerUpdatePage();
    expect(await checkboxAnswerUpdatePage.getPageTitle()).to.eq('Create or edit a Checkbox Answer');
    await checkboxAnswerUpdatePage.cancel();
  });

  it('should create and save CheckboxAnswers', async () => {
    const nbButtonsBeforeCreate = await checkboxAnswerComponentsPage.countDeleteButtons();

    await checkboxAnswerComponentsPage.clickOnCreateButton();
    await promise.all([]);
    await checkboxAnswerUpdatePage.save();
    expect(await checkboxAnswerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await checkboxAnswerComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CheckboxAnswer', async () => {
    const nbButtonsBeforeDelete = await checkboxAnswerComponentsPage.countDeleteButtons();
    await checkboxAnswerComponentsPage.clickOnLastDeleteButton();

    checkboxAnswerDeleteDialog = new CheckboxAnswerDeleteDialog();
    expect(await checkboxAnswerDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Checkbox Answer?');
    await checkboxAnswerDeleteDialog.clickOnConfirmButton();

    expect(await checkboxAnswerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

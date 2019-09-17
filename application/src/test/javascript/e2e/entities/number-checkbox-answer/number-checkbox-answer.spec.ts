/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  NumberCheckboxAnswerComponentsPage,
  NumberCheckboxAnswerDeleteDialog,
  NumberCheckboxAnswerUpdatePage
} from './number-checkbox-answer.page-object';

const expect = chai.expect;

describe('NumberCheckboxAnswer e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let numberCheckboxAnswerUpdatePage: NumberCheckboxAnswerUpdatePage;
  let numberCheckboxAnswerComponentsPage: NumberCheckboxAnswerComponentsPage;
  let numberCheckboxAnswerDeleteDialog: NumberCheckboxAnswerDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load NumberCheckboxAnswers', async () => {
    await navBarPage.goToEntity('number-checkbox-answer');
    numberCheckboxAnswerComponentsPage = new NumberCheckboxAnswerComponentsPage();
    await browser.wait(ec.visibilityOf(numberCheckboxAnswerComponentsPage.title), 5000);
    expect(await numberCheckboxAnswerComponentsPage.getTitle()).to.eq('Number Checkbox Answers');
  });

  it('should load create NumberCheckboxAnswer page', async () => {
    await numberCheckboxAnswerComponentsPage.clickOnCreateButton();
    numberCheckboxAnswerUpdatePage = new NumberCheckboxAnswerUpdatePage();
    expect(await numberCheckboxAnswerUpdatePage.getPageTitle()).to.eq('Create or edit a Number Checkbox Answer');
    await numberCheckboxAnswerUpdatePage.cancel();
  });

  it('should create and save NumberCheckboxAnswers', async () => {
    const nbButtonsBeforeCreate = await numberCheckboxAnswerComponentsPage.countDeleteButtons();

    await numberCheckboxAnswerComponentsPage.clickOnCreateButton();
    await promise.all([numberCheckboxAnswerUpdatePage.setNumberInput('5')]);
    expect(await numberCheckboxAnswerUpdatePage.getNumberInput()).to.eq('5', 'Expected number value to be equals to 5');
    const selectedCheck = numberCheckboxAnswerUpdatePage.getCheckInput();
    if (await selectedCheck.isSelected()) {
      await numberCheckboxAnswerUpdatePage.getCheckInput().click();
      expect(await numberCheckboxAnswerUpdatePage.getCheckInput().isSelected(), 'Expected check not to be selected').to.be.false;
    } else {
      await numberCheckboxAnswerUpdatePage.getCheckInput().click();
      expect(await numberCheckboxAnswerUpdatePage.getCheckInput().isSelected(), 'Expected check to be selected').to.be.true;
    }
    await numberCheckboxAnswerUpdatePage.save();
    expect(await numberCheckboxAnswerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await numberCheckboxAnswerComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last NumberCheckboxAnswer', async () => {
    const nbButtonsBeforeDelete = await numberCheckboxAnswerComponentsPage.countDeleteButtons();
    await numberCheckboxAnswerComponentsPage.clickOnLastDeleteButton();

    numberCheckboxAnswerDeleteDialog = new NumberCheckboxAnswerDeleteDialog();
    expect(await numberCheckboxAnswerDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Number Checkbox Answer?');
    await numberCheckboxAnswerDeleteDialog.clickOnConfirmButton();

    expect(await numberCheckboxAnswerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

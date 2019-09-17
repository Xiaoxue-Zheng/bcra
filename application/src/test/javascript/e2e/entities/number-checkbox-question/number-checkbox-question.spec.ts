/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  NumberCheckboxQuestionComponentsPage,
  NumberCheckboxQuestionDeleteDialog,
  NumberCheckboxQuestionUpdatePage
} from './number-checkbox-question.page-object';

const expect = chai.expect;

describe('NumberCheckboxQuestion e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let numberCheckboxQuestionUpdatePage: NumberCheckboxQuestionUpdatePage;
  let numberCheckboxQuestionComponentsPage: NumberCheckboxQuestionComponentsPage;
  let numberCheckboxQuestionDeleteDialog: NumberCheckboxQuestionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load NumberCheckboxQuestions', async () => {
    await navBarPage.goToEntity('number-checkbox-question');
    numberCheckboxQuestionComponentsPage = new NumberCheckboxQuestionComponentsPage();
    await browser.wait(ec.visibilityOf(numberCheckboxQuestionComponentsPage.title), 5000);
    expect(await numberCheckboxQuestionComponentsPage.getTitle()).to.eq('Number Checkbox Questions');
  });

  it('should load create NumberCheckboxQuestion page', async () => {
    await numberCheckboxQuestionComponentsPage.clickOnCreateButton();
    numberCheckboxQuestionUpdatePage = new NumberCheckboxQuestionUpdatePage();
    expect(await numberCheckboxQuestionUpdatePage.getPageTitle()).to.eq('Create or edit a Number Checkbox Question');
    await numberCheckboxQuestionUpdatePage.cancel();
  });

  it('should create and save NumberCheckboxQuestions', async () => {
    const nbButtonsBeforeCreate = await numberCheckboxQuestionComponentsPage.countDeleteButtons();

    await numberCheckboxQuestionComponentsPage.clickOnCreateButton();
    await promise.all([numberCheckboxQuestionUpdatePage.setMinimumInput('5'), numberCheckboxQuestionUpdatePage.setMaximumInput('5')]);
    expect(await numberCheckboxQuestionUpdatePage.getMinimumInput()).to.eq('5', 'Expected minimum value to be equals to 5');
    expect(await numberCheckboxQuestionUpdatePage.getMaximumInput()).to.eq('5', 'Expected maximum value to be equals to 5');
    await numberCheckboxQuestionUpdatePage.save();
    expect(await numberCheckboxQuestionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await numberCheckboxQuestionComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last NumberCheckboxQuestion', async () => {
    const nbButtonsBeforeDelete = await numberCheckboxQuestionComponentsPage.countDeleteButtons();
    await numberCheckboxQuestionComponentsPage.clickOnLastDeleteButton();

    numberCheckboxQuestionDeleteDialog = new NumberCheckboxQuestionDeleteDialog();
    expect(await numberCheckboxQuestionDeleteDialog.getDialogTitle()).to.eq(
      'Are you sure you want to delete this Number Checkbox Question?'
    );
    await numberCheckboxQuestionDeleteDialog.clickOnConfirmButton();

    expect(await numberCheckboxQuestionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RepeatQuestionComponentsPage, RepeatQuestionDeleteDialog, RepeatQuestionUpdatePage } from './repeat-question.page-object';

const expect = chai.expect;

describe('RepeatQuestion e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let repeatQuestionUpdatePage: RepeatQuestionUpdatePage;
  let repeatQuestionComponentsPage: RepeatQuestionComponentsPage;
  let repeatQuestionDeleteDialog: RepeatQuestionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RepeatQuestions', async () => {
    await navBarPage.goToEntity('repeat-question');
    repeatQuestionComponentsPage = new RepeatQuestionComponentsPage();
    await browser.wait(ec.visibilityOf(repeatQuestionComponentsPage.title), 5000);
    expect(await repeatQuestionComponentsPage.getTitle()).to.eq('Repeat Questions');
  });

  it('should load create RepeatQuestion page', async () => {
    await repeatQuestionComponentsPage.clickOnCreateButton();
    repeatQuestionUpdatePage = new RepeatQuestionUpdatePage();
    expect(await repeatQuestionUpdatePage.getPageTitle()).to.eq('Create or edit a Repeat Question');
    await repeatQuestionUpdatePage.cancel();
  });

  it('should create and save RepeatQuestions', async () => {
    const nbButtonsBeforeCreate = await repeatQuestionComponentsPage.countDeleteButtons();

    await repeatQuestionComponentsPage.clickOnCreateButton();
    await promise.all([repeatQuestionUpdatePage.setMaximumInput('5')]);
    expect(await repeatQuestionUpdatePage.getMaximumInput()).to.eq('5', 'Expected maximum value to be equals to 5');
    await repeatQuestionUpdatePage.save();
    expect(await repeatQuestionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await repeatQuestionComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last RepeatQuestion', async () => {
    const nbButtonsBeforeDelete = await repeatQuestionComponentsPage.countDeleteButtons();
    await repeatQuestionComponentsPage.clickOnLastDeleteButton();

    repeatQuestionDeleteDialog = new RepeatQuestionDeleteDialog();
    expect(await repeatQuestionDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Repeat Question?');
    await repeatQuestionDeleteDialog.clickOnConfirmButton();

    expect(await repeatQuestionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

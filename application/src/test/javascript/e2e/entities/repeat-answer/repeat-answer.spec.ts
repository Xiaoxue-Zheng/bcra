/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RepeatAnswerComponentsPage, RepeatAnswerDeleteDialog, RepeatAnswerUpdatePage } from './repeat-answer.page-object';

const expect = chai.expect;

describe('RepeatAnswer e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let repeatAnswerUpdatePage: RepeatAnswerUpdatePage;
  let repeatAnswerComponentsPage: RepeatAnswerComponentsPage;
  let repeatAnswerDeleteDialog: RepeatAnswerDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RepeatAnswers', async () => {
    await navBarPage.goToEntity('repeat-answer');
    repeatAnswerComponentsPage = new RepeatAnswerComponentsPage();
    await browser.wait(ec.visibilityOf(repeatAnswerComponentsPage.title), 5000);
    expect(await repeatAnswerComponentsPage.getTitle()).to.eq('Repeat Answers');
  });

  it('should load create RepeatAnswer page', async () => {
    await repeatAnswerComponentsPage.clickOnCreateButton();
    repeatAnswerUpdatePage = new RepeatAnswerUpdatePage();
    expect(await repeatAnswerUpdatePage.getPageTitle()).to.eq('Create or edit a Repeat Answer');
    await repeatAnswerUpdatePage.cancel();
  });

  it('should create and save RepeatAnswers', async () => {
    const nbButtonsBeforeCreate = await repeatAnswerComponentsPage.countDeleteButtons();

    await repeatAnswerComponentsPage.clickOnCreateButton();
    await promise.all([repeatAnswerUpdatePage.setQuantityInput('5')]);
    expect(await repeatAnswerUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');
    await repeatAnswerUpdatePage.save();
    expect(await repeatAnswerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await repeatAnswerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last RepeatAnswer', async () => {
    const nbButtonsBeforeDelete = await repeatAnswerComponentsPage.countDeleteButtons();
    await repeatAnswerComponentsPage.clickOnLastDeleteButton();

    repeatAnswerDeleteDialog = new RepeatAnswerDeleteDialog();
    expect(await repeatAnswerDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Repeat Answer?');
    await repeatAnswerDeleteDialog.clickOnConfirmButton();

    expect(await repeatAnswerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

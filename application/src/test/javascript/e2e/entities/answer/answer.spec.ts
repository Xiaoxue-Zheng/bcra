/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AnswerComponentsPage, AnswerDeleteDialog, AnswerUpdatePage } from './answer.page-object';

const expect = chai.expect;

describe('Answer e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let answerUpdatePage: AnswerUpdatePage;
  let answerComponentsPage: AnswerComponentsPage;
  let answerDeleteDialog: AnswerDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Answers', async () => {
    await navBarPage.goToEntity('answer');
    answerComponentsPage = new AnswerComponentsPage();
    await browser.wait(ec.visibilityOf(answerComponentsPage.title), 5000);
    expect(await answerComponentsPage.getTitle()).to.eq('Answers');
  });

  it('should load create Answer page', async () => {
    await answerComponentsPage.clickOnCreateButton();
    answerUpdatePage = new AnswerUpdatePage();
    expect(await answerUpdatePage.getPageTitle()).to.eq('Create or edit a Answer');
    await answerUpdatePage.cancel();
  });

  it('should create and save Answers', async () => {
    const nbButtonsBeforeCreate = await answerComponentsPage.countDeleteButtons();

    await answerComponentsPage.clickOnCreateButton();
    await promise.all([answerUpdatePage.answerGroupSelectLastOption(), answerUpdatePage.questionSelectLastOption()]);
    await answerUpdatePage.save();
    expect(await answerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await answerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Answer', async () => {
    const nbButtonsBeforeDelete = await answerComponentsPage.countDeleteButtons();
    await answerComponentsPage.clickOnLastDeleteButton();

    answerDeleteDialog = new AnswerDeleteDialog();
    expect(await answerDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Answer?');
    await answerDeleteDialog.clickOnConfirmButton();

    expect(await answerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

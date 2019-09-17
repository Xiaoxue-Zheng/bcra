/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RadioAnswerComponentsPage, RadioAnswerDeleteDialog, RadioAnswerUpdatePage } from './radio-answer.page-object';

const expect = chai.expect;

describe('RadioAnswer e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let radioAnswerUpdatePage: RadioAnswerUpdatePage;
  let radioAnswerComponentsPage: RadioAnswerComponentsPage;
  let radioAnswerDeleteDialog: RadioAnswerDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RadioAnswers', async () => {
    await navBarPage.goToEntity('radio-answer');
    radioAnswerComponentsPage = new RadioAnswerComponentsPage();
    await browser.wait(ec.visibilityOf(radioAnswerComponentsPage.title), 5000);
    expect(await radioAnswerComponentsPage.getTitle()).to.eq('Radio Answers');
  });

  it('should load create RadioAnswer page', async () => {
    await radioAnswerComponentsPage.clickOnCreateButton();
    radioAnswerUpdatePage = new RadioAnswerUpdatePage();
    expect(await radioAnswerUpdatePage.getPageTitle()).to.eq('Create or edit a Radio Answer');
    await radioAnswerUpdatePage.cancel();
  });

  it('should create and save RadioAnswers', async () => {
    const nbButtonsBeforeCreate = await radioAnswerComponentsPage.countDeleteButtons();

    await radioAnswerComponentsPage.clickOnCreateButton();
    await promise.all([]);
    await radioAnswerUpdatePage.save();
    expect(await radioAnswerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await radioAnswerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last RadioAnswer', async () => {
    const nbButtonsBeforeDelete = await radioAnswerComponentsPage.countDeleteButtons();
    await radioAnswerComponentsPage.clickOnLastDeleteButton();

    radioAnswerDeleteDialog = new RadioAnswerDeleteDialog();
    expect(await radioAnswerDeleteDialog.getDialogTitle()).to.eq('Are you sure you want to delete this Radio Answer?');
    await radioAnswerDeleteDialog.clickOnConfirmButton();

    expect(await radioAnswerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});

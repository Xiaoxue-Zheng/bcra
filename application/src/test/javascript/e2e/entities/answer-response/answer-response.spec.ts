/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AnswerResponseComponentsPage, AnswerResponseDeleteDialog, AnswerResponseUpdatePage } from './answer-response.page-object';

const expect = chai.expect;

describe('AnswerResponse e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let answerResponseUpdatePage: AnswerResponseUpdatePage;
  let answerResponseComponentsPage: AnswerResponseComponentsPage;
  /*let answerResponseDeleteDialog: AnswerResponseDeleteDialog;*/

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AnswerResponses', async () => {
    await navBarPage.goToEntity('answer-response');
    answerResponseComponentsPage = new AnswerResponseComponentsPage();
    await browser.wait(ec.visibilityOf(answerResponseComponentsPage.title), 5000);
    expect(await answerResponseComponentsPage.getTitle()).to.eq('Answer Responses');
  });

  it('should load create AnswerResponse page', async () => {
    await answerResponseComponentsPage.clickOnCreateButton();
    answerResponseUpdatePage = new AnswerResponseUpdatePage();
    expect(await answerResponseUpdatePage.getPageTitle()).to.eq('Create or edit a Answer Response');
    await answerResponseUpdatePage.cancel();
  });

  /* it('should create and save AnswerResponses', async () => {
        const nbButtonsBeforeCreate = await answerResponseComponentsPage.countDeleteButtons();

        await answerResponseComponentsPage.clickOnCreateButton();
        await promise.all([
            answerResponseUpdatePage.stateSelectLastOption(),
            answerResponseUpdatePage.setStatusInput('status'),
            answerResponseUpdatePage.questionnaireSelectLastOption(),
        ]);
        expect(await answerResponseUpdatePage.getStatusInput()).to.eq('status', 'Expected Status value to be equals to status');
        await answerResponseUpdatePage.save();
        expect(await answerResponseUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await answerResponseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    });*/

  /* it('should delete last AnswerResponse', async () => {
        const nbButtonsBeforeDelete = await answerResponseComponentsPage.countDeleteButtons();
        await answerResponseComponentsPage.clickOnLastDeleteButton();

        answerResponseDeleteDialog = new AnswerResponseDeleteDialog();
        expect(await answerResponseDeleteDialog.getDialogTitle())
            .to.eq('Are you sure you want to delete this Answer Response?');
        await answerResponseDeleteDialog.clickOnConfirmButton();

        expect(await answerResponseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });*/

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
